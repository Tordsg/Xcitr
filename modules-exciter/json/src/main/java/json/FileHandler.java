package json;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import Exciter.core;

public class FileHandler {

   private User user;

   private JSONParser parser = new JSONParser();
   private URL url = this.getClass().getResource("/tempSaveFile.json");

   public void saveUser(String name, int age, char... c) {
      JSONObject userData = new JSONObject();
      userData.put("name", name); // Type safety can't be avoided with simple-json
      userData.put("age", age);
      JSONArray userInformationArray = new JSONArray();
      for (char userInformationString : c) {
         userInformationArray.add(userInformationString);
      }
      userData.put("userInformation", userInformationArray);
      try {
         FileWriter fileWriter = new FileWriter(url.getFile());
         fileWriter.write(userData.toJSONString());
         fileWriter.close();
      } catch (FileNotFoundException e) {
         // TODO Handle this exception
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   public JSONObject readUser() {
      // TODO
      return null;
   }

}