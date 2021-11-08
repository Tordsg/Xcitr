package ui;

import java.io.IOException;
import java.rmi.ServerException;
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

    // TODO add support for multiple users
    public User discardCard(User current, User liked, User discard) throws ServerException {
        MediaType mediaType = MediaType.parse("application/json");
        User user = null;
        Response response = null;
        try {
            String sendString = mapper.writeValueAsString(List.of(liked, discard));
            Request request = new Request.Builder().url(url + "/like")
                    .header("Authorization", current.getId().toString()).post(RequestBody.create(sendString, mediaType))
                    .build();
            response = client.newCall(request).execute();
            ResponseBody body = response.body();
            user = mapper.readValue(body.string(), User.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (response.code() == 200) {
            return user;
        }
        throw new ServerException("Error: " + response.code());
    }

    public User createAccount(User user, String password) throws ServerException {
        MediaType mediaType = MediaType.parse("application/json");
        String hashedPassword = User.MD5Hash(password);
        User returnUser = null;
        Response response = null;
        try {
            String sendString = mapper.writeValueAsString(user);
            Request request = new Request.Builder().url(url + "/createAccount").header("Pass", hashedPassword)
                    .post(RequestBody.create(sendString, mediaType)).build();
            response = client.newCall(request).execute();
            ResponseBody body = response.body();
            returnUser = mapper.readValue(body.string(), User.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (response.code() == 200) {
            return returnUser;
        }
        throw new ServerException("Could not create account");
    }

    public User login(String mail, String password) throws ServerException {
        MediaType mediaType = MediaType.parse("application/json");
        User returnUser = null;
        String sendPassword = User.MD5Hash(password);
        Response response = null;
        try {
            String sendString = mapper.writeValueAsString(sendPassword);
            Request request = new Request.Builder().url(url + "/login").header("mail", mail)
                    .post(RequestBody.create(sendString, mediaType)).build();
            response = client.newCall(request).execute();
            ResponseBody body = response.body();
            returnUser = mapper.readValue(body.string(), User.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (response.code() == 200) {
            return returnUser;
        }
        throw new ServerException("Could not login");
    }

    public List<User> getMatches(User user) throws ServerException {
        List<User> returnUser = null;
        Response response = null;
        try {
            Request request = new Request.Builder().url(url + "/user/matches")
                    .header("Authorization", user.getId().toString()).build();
            response = client.newCall(request).execute();
            ResponseBody body = response.body();
            returnUser = mapper.readValue(body.string(),
                    mapper.getTypeFactory().constructCollectionType(List.class, User.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (response.code() == 200) {
            return returnUser;
        }
        throw new ServerException("Could not get matches");
    }

    public User updateInformation(User user) throws ServerException {
        MediaType mediaType = MediaType.parse("application/json");
        User returnUser = null;
        Response response = null;
        try {
            String sendString = mapper.writeValueAsString(user);
            Request request = new Request.Builder().url(url + "/user/update")
                    .header("Authorization", user.getId().toString()).post(RequestBody.create(sendString, mediaType))
                    .build();
            response = client.newCall(request).execute();
            ResponseBody body = response.body();
            returnUser = mapper.readValue(body.string(), User.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (response.code() == 200) {
            return returnUser;
        }
        throw new ServerException("Could not update information");
    }

    public User updatePassword(User user, String password) throws ServerException {
        MediaType mediaType = MediaType.parse("application/json");
        User returnUser = null;
        String sendPassword = User.MD5Hash(password);
        Response response = null;
        try {
            String sendString = mapper.writeValueAsString(sendPassword);
            Request request = new Request.Builder().url(url + "/user/update/password")
                    .header("Authorization", user.getId().toString()).post(RequestBody.create(sendString, mediaType))
                    .build();
            response = client.newCall(request).execute();
            ResponseBody body = response.body();
            returnUser = mapper.readValue(body.string(), User.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (response.code() == 200) {
            return returnUser;
        }
        throw new ServerException("Could not update password");
    }

    public List<User> getTwoUsers(User user) throws ServerException {
        List<User> returnUser = null;
        Response response = null;
        try {
            Request request = new Request.Builder().url(url + "/two").header("Authorization", user.getId().toString())
                    .build();
            response = client.newCall(request).execute();
            ResponseBody body = response.body();
            returnUser = mapper.readValue(body.string(),
                    mapper.getTypeFactory().constructCollectionType(List.class, User.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (response.code() == 200) {
            return returnUser;
        }
        throw new ServerException("Could not get two users");
    }

    public int getUserLikeCount(User user, User liked) throws ServerException {
        int returnInt = 0;
        Response response = null;
        try {
            Request request = new Request.Builder().url(url + "/user/likes")
                    .header("Authorization", user.getId().toString())
                    .header("mail", liked.getEmail()).build();
            response = client.newCall(request).execute();
            ResponseBody body = response.body();
            returnInt = mapper.readValue(body.string(), int.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (response.code() == 200) {
            return returnInt;
        }
        throw new ServerException("Could not get user count");
    }
}
