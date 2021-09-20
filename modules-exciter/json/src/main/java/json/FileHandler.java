package json;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import core.User;

public class FileHandler {

   private JSONParser parser = new JSONParser();
   private URL url = this.getClass().getResource("/tempSaveFile.json");

   public void saveUser(User user) {
      JSONObject userData = new JSONObject();
      userData.put("name", user.getName()); // Type safety can't be avoided with simple-json
      userData.put("age", user.getAge());
      userData.put("matches", user.getAlreadyMatched());
      userData.put("userInformation", user.getUserInformation());
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