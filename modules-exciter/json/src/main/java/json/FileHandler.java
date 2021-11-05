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
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import user.User;
import user.BotUser;

public class FileHandler {

  public FileHandler() {
    createFile();
  }

  private JSONParser parser = new JSONParser();
  String path = "../json/src/main/resources/UserData.JSON";

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
      userData.put("name", user.getName());
      userData.put("age", user.getAge());
      userData.put("matches", user.getMatches());
      userData.put("likes", user.getLikedUsers());
      userData.put("userInformation", user.getUserInformation());
      userData.put("email", user.getEmail());
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

    try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"))) {
      ArrayList<User> users = new ArrayList<>();
      JSONArray userArray = (JSONArray) parser.parse(fileReader);
      if (userArray.isEmpty()) {
        return users;
      }
      for (Object user : userArray) {
        JSONObject userData = (JSONObject) user;
        String name = String.valueOf(userData.get("name"));
        int age = Integer.parseInt(String.valueOf(userData.get("age")));

        List<String> alreadyMatched = parseJSONList((JSONArray) userData.get("matches"));

        String userInformation = String.valueOf(userData.get("userInformation"));
        String email = String.valueOf(userData.get("email"));
        boolean isBot = Boolean.parseBoolean(String.valueOf(userData.get("isBot")));
        if (isBot) {
          boolean isLikeBack = Boolean.parseBoolean(String.valueOf(userData.get("isLikeBack")));
          users.add(new BotUser(name, age, userInformation, email, isLikeBack));
        } else {
          String password = String.valueOf(userData.get("password"));
          users.add(new User(name, age, userInformation, alreadyMatched, email, password));
        }
      }
      return users;
    } catch (FileNotFoundException e) {
      createFile();
      return null;
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ParseException e) {
      return null;
    }
    return null;
  }

  /**
   * Parses a JSONArray into a List of Strings.
   *
   * @param jsonArray
   * @return List of user Emails
   */
  public List<String> parseJSONList(JSONArray jsonArray) {
    List<String> list = new ArrayList<>();
    for (Object object : jsonArray) {
      list.add(String.valueOf(object));
    }
    return list;
  }

  /**
   *
   * @param mail of a user
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

}