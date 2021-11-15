package json;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import user.BotUser;
import user.User;

/**
 * Main class for file handling.
 */

public class FileHandler {

  public FileHandler() {
    createFile();
  }

  private JSONParser parser = new JSONParser();
  // the "./" is there to make sure path works on mac
  String path = System.getProperty("user.home") + "/user.json";

  /**
   * Saves users to the JSON file. Will makes necessary checks for bot users to
   * differentiate between normal users and bot users.
   *
   * @param users to be saved
   */

  @SuppressWarnings("unchecked") // Type safety can't be avoided with simple-json
  public void saveUser(List<User> users) {
    JSONArray userArray = new JSONArray();
    for (User user : users) {
      JSONObject userData = new JSONObject();
      if (user instanceof BotUser) {
        userData.put("isBot", true);
        userData.put("password", null);
        userData.put("isLikeBack", ((BotUser) user).isLikeBack());
      } else {
        userData.put("isBot", false);
        userData.put("password", user.getPassword());
      }
      userData.put("UUID", String.valueOf(user.getId()));
      userData.put("name", user.getName());
      userData.put("age", user.getAge());
      userData.put("matches", user.getMatches());
      userData.put("likes", user.getLikedUsers());
      userData.put("userInformation", user.getUserInformation());
      userData.put("email", user.getEmail());
      userData.put("imageId", user.getImageId());
      userArray.add(userData);
    }
    try {
      // OutputStreamWriter is used to force UTF-8 encoding since fileWriter is using
      // wrong encoding on older mac models
      BufferedWriter fileWriter = new BufferedWriter(
          new OutputStreamWriter(new FileOutputStream(path), StandardCharsets.UTF_8));
      fileWriter.write(userArray.toJSONString());
      fileWriter.close();
    } catch (FileNotFoundException e) {
      createFile();
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Creates a new file.
   */

  public void createFile() {
    try {
      File file = new File(path);
      if (file.createNewFile()) {
        System.out.println("File created");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  /**
   * Loads users from the JSON file. Will makes necessary checks for bot users to
   * differentiate between normal users and bot users.
   *
   * @return list of users
   */

  public List<User> readUsers() {

    try (BufferedReader fileReader = new BufferedReader(
          new InputStreamReader(new FileInputStream(path), "UTF-8"))) {
      ArrayList<User> users = new ArrayList<>();
      JSONArray userArray = (JSONArray) parser.parse(fileReader);
      if (userArray.isEmpty()) {
        return users;
      }
      for (Object user : userArray) {
        JSONObject userData = (JSONObject) user;
        String idString = String.valueOf(userData.get("UUID"));
        UUID id = null;
        if (!idString.equals("null")) {
          id = UUID.fromString(idString);
        }
        String name = String.valueOf(userData.get("name"));
        int age = Integer.parseInt(String.valueOf(userData.get("age")));

        List<String> alreadyMatched = parseJsonList((JSONArray) userData.get("matches"));

        String userInformation = String.valueOf(userData.get("userInformation"));
        if (userInformation.equals("null")) {
          userInformation = "";
        }
        String email = String.valueOf(userData.get("email"));
        boolean isBot = Boolean.parseBoolean(String.valueOf(userData.get("isBot")));
        String password = String.valueOf(userData.get("password"));
        Map<String, Integer> likedUser = parseJsonMap((JSONObject) userData.get("likes"));
        Integer imageid = Integer
            .parseInt(String.valueOf(userData.get("imageId") == null
            ? 0 : String.valueOf(userData.get("imageId"))));
        if (isBot) {
          boolean isLikeBack = Boolean.parseBoolean(String.valueOf(userData.get("isLikeBack")));
          users.add(new BotUser(name, age, userInformation, email, isLikeBack, imageid));
        } else if (id != null) {
          users.add(new User(id, name, age,
              userInformation, alreadyMatched, email, password, likedUser, imageid));
        } else {
          // If user haven't gotten an id yet, it will neither have a imageid
          users.add(new User(name, age, userInformation, alreadyMatched, email, password));
        }
      }
      return users;
    } catch (FileNotFoundException e) {
      createFile();
      return null;
    } catch (IOException e) {
      return null;
    } catch (ParseException e) {
      return null;
    }
  }

  /**
   * Parses a JSONArray into a List of Strings.
   *
   * @param jsonArray
   *
   * @return List of user Emails
   */

  public static List<String> parseJsonList(JSONArray jsonArray) {
    List<String> list = new ArrayList<>();
    for (Object object : jsonArray) {
      list.add(String.valueOf(object));
    }
    return list;
  }

  /**
   * Parses a JSONObject into a list of Integers.
   *
   * @param jsonObj
   *
   * @return a map of liked users, null otherwise
   */

  @SuppressWarnings("unchecked")
  public static Map<String, Integer> parseJsonMap(JSONObject jsonObj) {
    Map<String, Object> map = (HashMap<String, Object>) jsonObj;
    Map<String, Integer> map2 = new HashMap<>();
    if (jsonObj == null) {
      return null;
    }
    map.forEach((key, value) -> map2.put(key, Integer.parseInt(String.valueOf(value))));
    return map2;
  }

  /**
   *Finds a user from their email adress.
   *
   * @param mail of a user
   *
   * @return user if mail exists in JSON file, null otherwise
   */

  public User getUser(String mail) {
    List<User> users = readUsers();
    for (User user : users) {
      if (user.getEmail().equals(mail)) {
        return user;
      }
    }
    return null;
  }

  /**
   * Finds user from their id.
   *
   * @param id
   *
   * @return user from their userID, null otherwise
   */

  public User getUserById(UUID id) {
    List<User> users = readUsers();
    for (User user : users) {
      if (user.getId() == null) {
        continue;
      }
      if (user.getId().equals(id)) {
        return user;
      }
    }
    return null;
  }

  /**
   * Finds all the users another user has liked from the userID.
   *
   * @param id UUID of user
   *
   * @return liked users of user with id
   */

  public Map<String, Integer> getLikedUsers(UUID id) {
    User user = getUserById(id);
    return user.getLikedUsers();
  }

}