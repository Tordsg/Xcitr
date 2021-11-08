package ui;

import java.io.IOException;
import java.net.ConnectException;
import java.rmi.ServerException;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import user.Chat;
import user.User;

public class ClientHandler {
    OkHttpClient client = new OkHttpClient();
    ObjectMapper mapper = new ObjectMapper();
    String url = "http://localhost:8080";

    public boolean pingServer() {
        Request requets = new Request.Builder().url(url).build();
        try {
            Response response = client.newCall(requets).execute();
            if (response.code() == 200 && response.body() != null) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public User discardCard(User current, User liked, User discard) throws ServerException {
        MediaType mediaType = MediaType.parse("application/json");
        try {
            String sendString = mapper.writeValueAsString(List.of(liked, discard));
            Request request = new Request.Builder().url(url + "/like")
                    .header("Authorization", current.getId().toString()).post(RequestBody.create(sendString, mediaType))
                    .build();
            Response response = client.newCall(request).execute();
            ResponseBody body = response.body();
            if (body != null) {
                User user = mapper.readValue(body.string(), User.class);
                if (response.code() == 200 && user != null) {
                    return user;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new ServerException("Server error");
    }

    public User createAccount(User user, String password) throws ServerException, ConnectException {
        MediaType mediaType = MediaType.parse("application/json");
        String hashedPassword = User.MD5Hash(password);
        try {
            String sendString = mapper.writeValueAsString(user);
            Request request = new Request.Builder().url(url + "/createAccount").header("Pass", hashedPassword)
                    .post(RequestBody.create(sendString, mediaType)).build();
            Response response = client.newCall(request).execute();
            ResponseBody body = response.body();
            if (body != null) {
                User returnUser = mapper.readValue(body.string(), User.class);
                if (response.code() == 200 && returnUser != null) {
                    return returnUser;
                }
            }
        } catch(ConnectException e){
            e.printStackTrace();
            throw new ConnectException("Server is not on");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        throw new ServerException("Could not create account");
    }

    public User login(String mail, String password) throws ServerException, ConnectException {
        MediaType mediaType = MediaType.parse("application/json");
        String sendPassword = User.MD5Hash(password);
        try {
            String sendString = mapper.writeValueAsString(sendPassword);
            Request request = new Request.Builder().url(url + "/login").header("mail", mail)
                    .post(RequestBody.create(sendString, mediaType)).build();
            Response response = client.newCall(request).execute();
            ResponseBody body = response.body();
            if (body != null) {
                User returnUser = mapper.readValue(body.string(), User.class);
                if (response.code() == 200 && returnUser != null) {
                    return returnUser;
                }
            }
        } catch(ConnectException e){
            e.printStackTrace();
            throw new ConnectException("Server is not on");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        throw new ServerException("Could not login");
    }

    public List<User> getMatches(User user) throws ServerException {
        try {
            Request request = new Request.Builder().url(url + "/user/matches")
                    .header("Authorization", user.getId().toString()).build();
            Response response = client.newCall(request).execute();
            ResponseBody body = response.body();
            if (body != null) {
                List<User> returnUser = mapper.readValue(body.string(),
                        mapper.getTypeFactory().constructCollectionType(List.class, User.class));
                if (response.code() == 200 && returnUser != null) {
                    return returnUser;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new ServerException("Could not get matches");
    }

    public User updateInformation(User user) throws ServerException {
        MediaType mediaType = MediaType.parse("application/json");
        try {
            String sendString = mapper.writeValueAsString(user);
            Request request = new Request.Builder().url(url + "/user/update")
                    .header("Authorization", user.getId().toString()).post(RequestBody.create(sendString, mediaType))
                    .build();
            Response response = client.newCall(request).execute();
            ResponseBody body = response.body();
            if (body != null) {
                User returnUser = mapper.readValue(body.string(), User.class);
                if (response.code() == 200 && returnUser != null) {
                    return returnUser;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new ServerException("Could not update information");
    }

    public User updatePassword(User user, String password) throws ServerException {
        MediaType mediaType = MediaType.parse("application/json");
        String sendPassword = User.MD5Hash(password);
        try {
            String sendString = mapper.writeValueAsString(sendPassword);
            Request request = new Request.Builder().url(url + "/user/update/password")
                    .header("Authorization", user.getId().toString()).post(RequestBody.create(sendString, mediaType))
                    .build();
            Response response = client.newCall(request).execute();
            ResponseBody body = response.body();
            if (body != null) {
                User returnUser = mapper.readValue(body.string(), User.class);
                if (response.code() == 200 && returnUser != null) {
                    return returnUser;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new ServerException("Could not update password");
    }

    public List<User> getTwoUsers(User user) throws ServerException {
        try {
            Request request = new Request.Builder().url(url + "/two").header("Authorization", user.getId().toString())
                    .build();
            Response response = client.newCall(request).execute();
            ResponseBody body = response.body();
            if (body != null) {
                List<User> returnUser = mapper.readValue(body.string(),
                        mapper.getTypeFactory().constructCollectionType(List.class, User.class));
                if (response.code() == 200 && returnUser != null) {
                    return returnUser;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new ServerException("Could not get two users");
    }

    public int getUserLikeCount(User user, User liked) throws ServerException {
        try {
            Request request = new Request.Builder().url(url + "/user/likes")
                    .header("Authorization", user.getId().toString()).header("mail", liked.getEmail()).build();
            Response response = client.newCall(request).execute();
            ResponseBody body = response.body();
            if (body != null) {
                int returnInt = mapper.readValue(body.string(), int.class);
                if (response.code() == 200) {
                    return returnInt;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new ServerException("Could not get user count");
    }

    public User getUser(User user, List<User> users) throws ServerException {
        try {
            MediaType mediaType = MediaType.parse("application/json");
            String sendString = mapper.writeValueAsString(users);
            Request request = new Request.Builder().url(url + "/user/new")
                    .header("Authorization", user.getId().toString()).post(RequestBody.create(sendString, mediaType))
                    .build();
            Response response = client.newCall(request).execute();
            ResponseBody body = response.body();
            if (body != null) {
                User returnUser = mapper.readValue(body.string(), User.class);
                if (response.code() == 200 && returnUser != null) {
                    return returnUser;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new ServerException("Could not get user");
    }

    public Chat sendMessage(User user, User receiver, String message) throws ServerException {
        MediaType mediaType = MediaType.parse("application/json");
        try {
            String sendString = mapper.writeValueAsString(message);
            Request request = new Request.Builder().url(url + "/chat")
                    .header("Authorization", user.getId().toString())
                    .header("mail", receiver.getEmail())
                    .post(RequestBody.create(sendString, mediaType)).build();
            Response response = client.newCall(request).execute();
            ResponseBody body = response.body();
            if (body != null) {
                Chat returnChat = mapper.readValue(body.string(), Chat.class);
                if (response.code() == 200 && returnChat != null) {
                    return returnChat;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new ServerException("Could not send message");
    }
}
