package json;

import java.io.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import core.BotUser;
import core.User;

public class FileHandler {

   public FileHandler() {
      createFile();
   }

   private JSONParser parser = new JSONParser();
   String path = "../json/src/main/resources/tempSaveFile.JSON";

   @SuppressWarnings("unchecked") // Type safety can't be avoided with simple-json
   public void saveUser(ArrayList<User> users) {
      JSONArray userArray = new JSONArray();
      for (User user : users) {
         JSONObject userData = new JSONObject();
         if (user instanceof BotUser) {
            userData.put("isBot", true);
            userData.put("password", null);
         } else {
            userData.put("isBot", false);
            userData.put("password", user.getPassword());
         }
         userData.put("name", user.getName());
         userData.put("age", user.getAge());
         userData.put("matches", user.getAlreadyMatched());
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

   public ArrayList<User> readUsers() {

      try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"))) {
         ArrayList<User> users = new ArrayList<>();
         JSONArray userArray = (JSONArray) parser.parse(fileReader);
         for (Object user : userArray) {
            JSONObject userData = (JSONObject) user;
            String name = userData.get("name").toString();
            int age = Integer.parseInt(userData.get("age").toString());
            HashMap<String, Integer> alreadyMatched = parseUserMatchesJSON((JSONObject) userData.get("matches"));
            String userInformation = userData.get("userInformation").toString();
            String email = userData.get("email").toString();
            users.add(new User(name, age, userInformation, alreadyMatched, email));
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

   public HashMap<String, Integer> parseUserMatchesJSON(JSONObject obj) {
      HashMap<String, Integer> matchedUsers = new HashMap<>();
      Iterator<?> keys = obj.keySet().iterator();
      while (keys.hasNext()) {
         Object localKey = keys.next();
         matchedUsers.put(localKey.toString(), Integer.parseInt(obj.get(localKey).toString()));
      }
      return matchedUsers;
   }

   public User getUser(String mail) {
      ArrayList<User> users = readUsers();
      for (User user : users) {
         if (user.getEmail().equals(mail)) {
            return user;
         }
      }
      return null;
   }

}