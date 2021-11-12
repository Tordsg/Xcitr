package restserver;

import core.Exciter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import json.FileHandler;
import json.MessageHandler;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import user.BotUser;
import user.Chat;
import user.User;

@RestController
public class ServerController {

  private final Exciter excite = ExciterApplication.excite;
  private final FileHandler fileHandler = new FileHandler();
  private final MessageHandler messageHandler = new MessageHandler();

  @GetMapping("/")
  public String index() {
    return "Hello World! Welcome to Exciter";
  }

  /**
   *
   * @param id UUID of the user client wish to get
   * @return User object
   */
  @GetMapping(value = "/user")
  public User currentUser(@RequestHeader("Authorization") UUID id) {
    User user = excite.getUserById(id);
    if (user == null) {
      throw new IllegalArgumentException("User does not exist");
    }
    return user;
  }

  /**
   * @param user userobject to be create
   * @param pass password of the user
   *
   * @return User object
   */
  @PostMapping(value = "/createAccount")
  public User createAccount(@RequestBody User user, @RequestHeader("Pass") String pass) {
    if (excite.getUserByEmail(user.getEmail()) != null) {
      throw new IllegalArgumentException("User already exists");
    }
    user.setPasswordNoHash(pass);
    user.setId(UUID.randomUUID());
    excite.addUser(user);
    fileHandler.saveUser(excite.getAllUsers());
    return user;
  }

  /**
   * Exception handler for bad requests
   */
  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseStatus(value = org.springframework.http.HttpStatus.BAD_REQUEST)
  public String handleIllegalArgumentException(IllegalArgumentException e) {
    return e.getMessage();
  }

  /**
   * @param id UUID of the user
   *
   * @return List of all the matches the user has
   */
  @GetMapping(value = "/user/matches")
  public List<User> getMatches(@RequestHeader("Authorization") UUID id) {
    List<User> matches = new ArrayList<>();
    User thisUser = excite.getUserById(id);
    List<String> matchesEmail = thisUser.getMatches();
    for (User user : excite.getAllUsers()) {
      if (matchesEmail.contains(user.getEmail())) {
        matches.add(user);
      }
    }
    return matches;
  }

  /**
   * @param mail email of the user
   * @param pass password of the user
   *
   * @return User object if it exists
   * @throws IllegalArgumentException if the user does not exist
   */
  @PostMapping(value = "/login")
  public User setLoginUser(@RequestHeader("mail") String mail, @RequestBody String password) {
    User user = excite.getUserByEmail(mail);
    if (user != null) {
      if (user.getPassword().equals(password.replace("\"", ""))) {
        return excite.getUserByEmail(mail);
      } else {
        throw new IllegalArgumentException("Wrong password");
      }

    }
    throw new IllegalArgumentException("User does not exist");
  }

  /**
   * @param id   UUID of the user
   * @param user user object to be updated
   *
   * @return User object
   */
  @PostMapping(value = "/user/update")
  public User updateUserInfo(@RequestHeader("Authorization") UUID id, @RequestBody User user) {
    if (excite.getUserById(id) == null) {
      throw new IllegalAccessError("You do not have permission to update this user");
    }
    User thisUser = excite.getUserByEmail(user.getEmail());
    thisUser.setImageId(user.getImageId());
    thisUser.setName(user.getName());
    thisUser.setAge(user.getAge());
    thisUser.setUserInformation(user.getUserInformation());
    fileHandler.saveUser(excite.getAllUsers());

    return thisUser;
  }

  /**
   *
   * @param id       UUID of the user
   * @param password new password
   *
   * @return User object with updated password
   */
  @PostMapping(value = "/user/update/password")
  public User updateUserPassword(@RequestHeader("Authorization") UUID id, @RequestBody String password) {
    if (excite.getUserById(id) == null) {
      throw new IllegalAccessError("You do not have permission to update this user");
    }
    User thisUser = excite.getUserById(id);
    thisUser.setPasswordNoHash(password.replace("\"", ""));
    fileHandler.saveUser(excite.getAllUsers());
    return thisUser;
  }

  /**
   *
   * illegalAccessError if client tries to do illegal request
   */
  @ExceptionHandler(IllegalAccessError.class)
  @ResponseStatus(value = org.springframework.http.HttpStatus.FORBIDDEN)
  public String handleIllegalAccessError(IllegalAccessError e) {
    return e.getMessage();
  }

  /**
   *
   * @param id    UUID of the user
   * @param users client creates a list of users
   * @return new user
   *
   * @apiNote the first user in the list is the one who is liked and the second is
   *          the one who is discarded. This is to ensure the user is return isn't
   *          anyone of them
   */
  @PostMapping(value = "/like")
  public User likeUser(@RequestHeader("Authorization") UUID id, @RequestBody List<User> users) {
    User thisUser = excite.getUserById(id);
    if (excite.getUserByEmail(users.get(0).getEmail()) == null || excite.getUserByEmail(users.get(1).getEmail()) == null
        || excite.getUserByEmail(thisUser.getEmail()) == null) {
      throw new IllegalArgumentException("User does not exist");
    }
    excite.likePerson(thisUser, excite.getUserByEmail(users.get(0).getEmail()));
    excite.resetLikes(thisUser, excite.getUserByEmail(users.get(1).getEmail()));
    List<User> tmp = List.of(thisUser, excite.getUserByEmail(users.get(0).getEmail()),
        excite.getUserByEmail(users.get(1).getEmail()));
    return excite.getNextRandomUser(tmp);

  }

  /**
   *
   * @param id UUID of the user
   * @return list with two unique users
   *
   * @apiNote core ensures the list does not contain the user who is sending the
   *          request
   */
  @GetMapping(value = "/two")
  public List<User> getTwoUsers(@RequestHeader("Authorization") UUID id) {
    User thisUser = excite.getUserById(id);
    if (thisUser == null) {
      throw new IllegalArgumentException("User does not exist");
    }
    return excite.getTwoUniqueUsers(thisUser);
  }

  /**
   * @param id   UUID of the user
   * @param mail email of the user to check likecount against
   *
   * @return number of likes
   */
  @GetMapping(value = "/user/likes")
  public int getLikes(@RequestHeader("Authorization") UUID id, @RequestHeader("mail") String user) {
    User thisUser = excite.getUserById(id);
    User likeUser = excite.getUserByEmail(user);
    if (thisUser == null || likeUser == null) {
      throw new IllegalArgumentException("User does not exist");
    }
    if (thisUser.getLikedUsers().containsKey(likeUser.getEmail())) {
      return thisUser.getLikedUsers().get(likeUser.getEmail());
    }
    return 0;
  }

  /**
   * @param id    UUID of the user
   * @param users list of users to avoid
   *
   * @return new user that is not in the list or the sender
   *
   * @apiNote this method is used to get new users without doing a like action
   */
  @PostMapping(value = "/user/new")
  public User postMethodName(@RequestHeader("Authorization") UUID id, @RequestBody List<User> users) {
    User thisUser = excite.getUserById(id);
    List<String> list = users.stream().map(User::getEmail).collect(Collectors.toList());
    List<User> tmp = excite.getUsersFromList(list);
    tmp.add(thisUser);
    if (tmp.contains(null)) {
      throw new IllegalArgumentException("User does not exist");
    }
    User returnUser = excite.getNextRandomUser(tmp);
    return returnUser;
  }

  /**
   *
   * @param id UUID of the user
   * @param mail email of the user whos reciving the message
   * @param message message to be sent
   * @return the whole chat
   *
   * @apiNote this method will check if reciver is a bot and
   *          make them respond if true
   */
  @PostMapping(value = "/message")
  public Chat sendChat(@RequestHeader("Authorization") UUID id, @RequestHeader("mail") String mail,
      @RequestBody String message) {
    User user = excite.getUserById(id);
    User user2 = excite.getUserByEmail(mail);
    if (user == null || user2 == null) {
      throw new IllegalArgumentException("User does not exist");
    }
    Chat chat = messageHandler.getChat(user.getEmail(), mail);
    if (chat == null) {
      chat = new Chat(user.getEmail(), mail);
    }
    chat.sendMeesage(user.getEmail(), message);
    if (user2 instanceof BotUser) {
      chat.sendMeesage(mail, ((BotUser) user2).reply());
    }
    messageHandler.saveChat(chat);
    return chat;
  }

  /**
   *
   * @param id UUID of the user
   * @param mail email of the user whos the chat is with
   *
   * @return the whole chat
   */
  @GetMapping(value = "/message")
  public Chat getChat(@RequestHeader("Authorization") UUID id, @RequestHeader("mail") String mail) {
    User user = excite.getUserById(id);
    User user2 = excite.getUserByEmail(mail);
    if (user == null || user2 == null) {
      throw new IllegalArgumentException("User or chat does not exist");
    }
    Chat chat = messageHandler.getChat(user.getEmail(), mail);
    if (chat == null) {
      chat = new Chat(user.getEmail(), mail);
    }
    return chat;
  }

  /**
   *
   * @param mail email of the user who's going to be deleted
   * @return true if the user was deleted
   * @throws Exception if the user doesn't exist
   */
  @DeleteMapping(value = "/user")
  public boolean deleteUser(@RequestHeader("mail") String mail) throws Exception {
    User user = excite.getUserByEmail(mail);
    if (user == null) {
      throw new Exception("User not found on :: " + mail);
    }
    excite.clearUser(user);
    fileHandler.saveUser(excite.getAllUsers());
    if (fileHandler.getUser(mail) == null) {
      return true;
    }
    return false;
  }

  /**
   * This server is a teapot and cannot brew coffee
   */
  @GetMapping(value = "/brew")
  @ResponseStatus(value = org.springframework.http.HttpStatus.I_AM_A_TEAPOT)
  public String brew() {
    return "I'm a teapot";
  }
}
