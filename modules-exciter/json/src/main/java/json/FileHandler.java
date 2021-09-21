package json;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import core.User;

public class FileHandler {

   public FileHandler() {
   }

   private JSONParser parser = new JSONParser();
   String path = "src/main/resources/tempSaveFile.JSON";

   @SuppressWarnings("unchecked") // Type safety can't be avoided with simple-json
   public void saveUser(User user) {
      JSONObject userData = new JSONObject();
      userData.put("name", user.getName());
      userData.put("age", user.getAge());
      userData.put("matches", user.getAlreadyMatched());
      userData.put("userInformation", user.getUserInformation());
      try {
         FileWriter fileWriter = new FileWriter(path);
         fileWriter.write(userData.toJSONString());
         fileWriter.close();
      } catch (FileNotFoundException e) {
         // TODO Handle this exception
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   public void createFile() {
      try {
         File file = new File(path);
         if(file.createNewFile()) {
            System.out.println("File created");
         }
      }  catch (Exception e) {
         e.printStackTrace();
      }

   }

   public User readUser() {

      try (FileReader fileReader = new FileReader(path)) {
         Object obj = parser.parse(fileReader);
         JSONArray userArray = (JSONArray) obj;
         return new User(userArray.get(0).toString(), Integer.parseInt(userArray.get(1).toString()),
               userArray.get(2).toString());

      } catch (FileNotFoundException e) {
         // TODO Handle this exception
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      } catch (ParseException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      return null;
   }

}