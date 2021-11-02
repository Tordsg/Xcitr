package ui;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import user.User;

import com.fasterxml.jackson.databind.ObjectMapper;
public class ClientHandler {
    OkHttpClient client = new OkHttpClient();
    ObjectMapper mapper = new ObjectMapper();
    User user;
    String url = "http://localhost:8080";
    public User getCurrentUser(){
        Request request = new Request.Builder().url(url+"/user").build();
        try {
            ResponseBody response = client.newCall(request).execute().body();
            User newUser = mapper.readValue(response.string(), User.class);
            return newUser;
        } catch (Exception e) {
        }
        return null;
    }
    
}
