package ui;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import user.User;

public class ClientHandler {
    OkHttpClient client = new OkHttpClient();
    ObjectMapper mapper = new ObjectMapper();
    User user;
    String url = "http://localhost:8080";

    public boolean pingServer() {
        Request requets = new Request.Builder().url(url).build();

        Response response = null;
        try {
            response = client.newCall(requets).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (response.code() == 200) {
            return true;
        }
        return false;
    }

    //TODO add support for multiple users
    public User discardCard(User current, User liked, User discard){
        MediaType mediaType = MediaType.parse("application/json");
        String sendString;
        Request request;
        User user = null;
        try {
            sendString = mapper.writeValueAsString(List.of(liked,discard));
            request = new Request.Builder().url(url+"/like")
                                .header("Authorization", current.getId().toString())
                                .post(RequestBody.create(sendString, mediaType)).build();
            ResponseBody response = client.newCall(request).execute().body();
            user = mapper.readValue(response.string(), User.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return user;
    }

    public User createAccount(User user, String password) {
        MediaType mediaType = MediaType.parse("application/json");
        String sendString;
        Request request;
        String hashedPassword = User.MD5Hash(password);
        User returnUser = null;
        try {
            sendString = mapper.writeValueAsString(user);
            request = new Request.Builder().url(url+"/createAccount")
                                .header("Pass", hashedPassword)
                                .post(RequestBody.create(sendString, mediaType)).build();
            ResponseBody response = client.newCall(request).execute().body();
            returnUser = mapper.readValue(response.string(), User.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnUser;
    }

    public User signIn(String mail, String password) {
        MediaType mediaType = MediaType.parse("application/json");
        String sendString;
        Request request;
        User returnUser = null;
        try {
            sendString = mapper.writeValueAsString(new User(mail));
            request = new Request.Builder().url(url+"/signIn")
                                .header("Pass", User.MD5Hash(password))
                                .post(RequestBody.create(sendString, mediaType)).build();
            ResponseBody response = client.newCall(request).execute().body();
            returnUser = mapper.readValue(response.string(), User.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnUser;
    }

}
