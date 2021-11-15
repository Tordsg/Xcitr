package ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.ConnectException;
import java.rmi.ServerException;
import java.util.List;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import user.Chat;
import user.User;

/**
 * Class to handle the REST client.
 */

public class ClientHandler {
  OkHttpClient client = new OkHttpClient();
  ObjectMapper mapper = new ObjectMapper();
  String url = "http://localhost:8080";

  /**
   * Method to ping the server.
   *
   * @return boolean
   */

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

  /**
   * Discards the card of a user the current user doesn´t like.
   *
   * @param current the User object logged into the application
   * @param liked a User object that the current user has liked
   * @param discard a User object that the current user doesn´t like
   *
   * @return the User object that the current user doesn´t like
   *
   * @throws ServerException IOException
   * @throws ConnectException IOException
   */

  public User discardCard(User current, User liked, User discard) throws ServerException, ConnectException {
    MediaType mediaType = MediaType.parse("application/json");
    try {
      String sendString = mapper.writeValueAsString(List.of(liked, discard));
      Request request = new Request.Builder().url(url + "/like").header("Authorization", current.getId().toString())
          .post(RequestBody.create(sendString, mediaType)).build();
      Response response = client.newCall(request).execute();
      ResponseBody body = response.body();
      if (body != null) {
        User user = mapper.readValue(body.string(), User.class);
        if (response.code() == 200 && user != null) {
          return user;
        }
      }
    } catch (IOException e) {
      throw new ConnectException("Could not connect to server");
    }
    throw new ServerException("Server error");
  }

  /**
   * Creating a new account on the application through the server.
   *
   * @param user User object
   * @param password string of the users password
   *
   * @return a new user
   *
   * @throws ServerException IOException
   * @throws ConnectException IOException
   */

  public User createAccount(User user, String password) throws ServerException, ConnectException {
    MediaType mediaType = MediaType.parse("application/json");
    String hashedPassword = User.md5Hash(password);
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
    } catch (ConnectException e) {
      e.printStackTrace();
      throw new ConnectException("Server is not on");
    } catch (IOException e) {
      e.printStackTrace();
    }
    throw new ServerException("Could not create account");
  }

  /**
   * Method to login to the application through the server.
   *
   * @param mail string of a users email
   * @param password string of a users password
   *
   * @return the user that has logged into the application
   *
   * @throws ServerException IOException
   * @throws ConnectException IOException
   */

  public User login(String mail, String password) throws ServerException, ConnectException {
    MediaType mediaType = MediaType.parse("application/json");
    String sendPassword = User.md5Hash(password);
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
    } catch (ConnectException e) {
      throw new ConnectException("Server is not on");
    } catch (IOException e) {
      e.printStackTrace();
    }
    throw new ServerException("Could not login");
  }

  /**
   * Accesses a users matches through the server.
   *
   * @param user User object
   *
   * @return list of the users matches
   *
   * @throws ServerException IOException
   * @throws ConnectException IOException
   */

  public List<User> getMatches(User user) throws ServerException, ConnectException {
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
      throw new ConnectException("Can not connect to server");
    }
    throw new ServerException("Could not get matches");
  }

  /**
   * Method to update the users biographical information through the server.
   *
   * @param user User object
   *
   * @return a user with updated bio
   *
   * @throws ServerException IOException
   */

  public User updateInformation(User user) throws ServerException {
    MediaType mediaType = MediaType.parse("application/json");
    try {
      String sendString = mapper.writeValueAsString(user);
      Request request = new Request.Builder().url(url + "/user/update").header("Authorization", user.getId().toString())
          .post(RequestBody.create(sendString, mediaType)).build();
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

  /**
   * Updates the users password.
   *
   * @param user User object
   * @param password string of the users password
   *
   * @return user with updated password
   *
   * @throws ServerException IOException
   * @throws ConnectException IOException
   */

  public User updatePassword(User user, String password) throws ServerException, ConnectException {
    MediaType mediaType = MediaType.parse("application/json");
    String sendPassword = User.md5Hash(password);
    try {
      String sendString = mapper.writeValueAsString(sendPassword);
      Request request = new Request.Builder().url(url + "/user/update/password")
          .header("Authorization", user.getId().toString()).post(RequestBody.create(sendString, mediaType)).build();
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
      throw new ConnectException("Can not find server");
    }
    throw new ServerException("Could not update password");
  }

  /**
   * Gets two users.
   *
   * @param user User object
   *
   * @return a list of two users
   *
   * @throws ServerException IOException
   * @throws ConnectException IOException
   */

  public List<User> getTwoUsers(User user) throws ServerException, ConnectException {
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
      throw new ConnectException("Can not find server");
    }
    throw new ServerException("Could not get two users");
  }

  /**
   * Finds how many times the current user has liked another user.
   *
   * @param user User object
   * @param liked User object that has been liked by user
   *
   * @return int of how many times in a row the user has liked the other user
   *
   * @throws ServerException IOException
   * @throws ConnectException IOException
   */

  public int getUserLikeCount(User user, User liked) throws ServerException, ConnectException {
    try {
      Request request = new Request.Builder().url(url + "/user/likes").header("Authorization", user.getId().toString())
          .header("mail", liked.getEmail()).build();
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
      throw new ConnectException("Can not connect to server");
    }
    throw new ServerException("Could not get user count");
  }

  /**
   * Finds a user from the list of users who have signed up for the application.
   *
   * @param user User object
   * @param users list of users
   *
   * @return user
   *
   * @throws ServerException IOException
   */
  public User getUser(User user, List<User> users) throws ServerException {
    try {
      MediaType mediaType = MediaType.parse("application/json");
      String sendString = mapper.writeValueAsString(users);
      Request request = new Request.Builder().url(url + "/user/new").header("Authorization", user.getId().toString())
          .post(RequestBody.create(sendString, mediaType)).build();
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

  /**
   * Sending a message over chat through server.
   *
   * @param user User object
   * @param receiver User object
   * @param message string of the message
   *
   * @return chat message
   *
   * @throws ServerException IOException
   * @throws ConnectException IOException
   */
  public Chat sendMessage(User user, User receiver, String message) throws ServerException, ConnectException {
    MediaType mediaType = MediaType.parse("application/json");
    try {
      String sendString = mapper.writeValueAsString(message);
      Request request = new Request.Builder().url(url + "/message").header("Authorization", user.getId().toString())
          .header("mail", receiver.getEmail()).post(RequestBody.create(sendString, mediaType)).build();
      Response response = client.newCall(request).execute();
      ResponseBody body = response.body();
      if (body != null) {
        Chat returnChat = mapper.readValue(body.string(), Chat.class);
        if (response.code() == 200 && returnChat != null) {
          return returnChat;
        }
      }
    } catch (IOException e) {
      throw new ConnectException("Can not connect to server");

    }
    throw new ServerException("Could not send message");
  }

  /**
   * Accessing the chat between two users.
   *
   * @param user User object
   * @param user2 User object
   *
   * @return chat between two users
   *
   * @throws ServerException IOException
   * @throws ConnectException IOException
   */
  public Chat getChat(User user, User user2) throws ServerException, ConnectException {
    try {
      Request request = new Request.Builder().url(url + "/message").header("Authorization", user.getId().toString())
          .header("mail", user2.getEmail()).build();
      Response response = client.newCall(request).execute();
      ResponseBody body = response.body();
      if (body != null) {
        Chat returnChat = mapper.readValue(body.string(), Chat.class);
        if (response.code() == 200 && returnChat != null) {
          return returnChat;
        }
      }
    } catch (IOException e) {
      throw new ConnectException("Can not connect to server");
    }
    throw new ServerException("Could not send message");
  }

  /**
   * Deleting a user from the application.
   *
   * @param user User object
   *
   * @return boolean
   *
   * @throws ServerException IOException
   * @throws ConnectException IOException
   */
  public boolean deleteUser(User user) throws ServerException, ConnectException {
    try {
      Request request = new Request.Builder().url(url + "/user").header("mail", user.getEmail())
          .delete().build();
      Response response = client.newCall(request).execute();
      ResponseBody body = response.body();
      if (body != null) {
        return true;
      }
    } catch (IOException e) {
      throw new ConnectException("Can not connect to server");
    }
    throw new ServerException("Could not delete user");
  }

}
