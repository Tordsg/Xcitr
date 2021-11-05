package user;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * This class configure a user.
 */
@JsonDeserialize(using = UserDeserializer.class)
public class User {

  private String name;
  private int age;
  private String userInformation;
  private String email;
  private HashMap<User, Integer> likedUsers = new HashMap<>();
  private List<String> matches = new ArrayList<>();
  @JsonIgnore
  private String password = null;

  /**
   * Constructor for User class.
   *
   * @param name
   * @param age
   * @param userInformation
   * @param matches
   * @param email
   * @param password
   * @apiNote This constructor is to only be used by the filehandler class.
   */

  public User(String name, int age, String userInformation, List<String> matches, String email, String password) {
    this.userInformation = userInformation;
    this.matches = matches;
    setName(name);
    setEmail(email);
    this.password = password;
    setAge(age);
  }

  /**
   *
   * @param name
   * @param age
   * @param userInformation
   * @param matches
   * @param email
   */

  public User(String name, int age, String userInformation, List<String> matches, String email) {
    this.userInformation = userInformation;
    this.matches = matches;
    setName(name);
    setEmail(email);
    setAge(age);
  }

  /**
   * Constructor for User class.
   *
   * @param name
   * @param age
   * @param userInformation
   * @param email
   */

  public User(String name, int age, String userInformation, String email) {
    this.userInformation = userInformation;
    setName(name);
    setEmail(email);
    setAge(age);
  }

  /**
   * Constructor for User class.
   *
   * @param name
   * @param age
   * @param email
   */
  public User(String name, int age, String email) {
    this.name = name;
    setAge(age);
    setEmail(email);
  }

  public User() {
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    if (name.length() < 2) {
      throw new IllegalArgumentException("Name must be at least 2 letters");
    }
    if (!name.matches("^[ a-zA-Z]+$")) {
      throw new IllegalArgumentException("Name must only contain letters");
    }
    this.name = name;

  }

  public int getAge() {
    return this.age;
  }

  public void setEmail(String email) {
    if (!email.contains("@")) {
      throw new IllegalArgumentException("Email must contain @");
    }
    this.email = email;
  }

  public String getEmail() {
    return this.email;
  }

  public void setUserInformation(String userInformation) {
    this.userInformation = userInformation;
  }

  public String getUserInformation() {
    return this.userInformation;
  }

  public void setAge(int age) {
    if (age < 0) {
      throw new IllegalArgumentException("Age cannot be negative");
    }
    this.age = age;
  }

  public void setPassword(String password) {
    this.password = MD5Hash(password);
    ;
  }

  /**
   * This method is used to hash the password.
   *
   * @param password string.
   * @return MD5 hash of password.
   */

  public static String MD5Hash(String password) {
    String outString = null;
    try {
      MessageDigest md = MessageDigest.getInstance("MD5");
      md.update(password.getBytes(Charset.forName("UTF-8")));
      byte[] bytes = md.digest();
      StringBuilder sb = new StringBuilder();

      for (int i = 0; i < bytes.length; i++) {
        sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
      }
      outString = sb.toString();
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    return outString;
  }

  public String getPassword() {
    return this.password;
  }

  public HashMap<User, Integer> getLikedUsers() {
    return new HashMap<>(likedUsers);
  }

  public void fireOnLike(User match) {
    this.addUserOnMatch(match);
  }

  public void addMatch(String matches) {
    this.matches.add(matches);
  }

  public List<String> getMatches() {
    return new ArrayList<>(matches);
  }

  public boolean containsPreviousMatch(User match) {
    return likedUsers.containsKey(match);
  }

  /**
   * Adds a user to the likedUsers HashMap.
   *
   * @param match The user to be added.
   *
   */

  public void addUserOnMatch(User match) {
    if (!likedUsers.containsKey(match)) {
      likedUsers.put(match, 1);
    } else {
      likedUsers.put(match, likedUsers.get(match) + 1);
    }
  }

  /**
   * If a user is liked sufficiently, like count resets to 0.
   */

  public void resetUserMatch(User user) {
    if (likedUsers.containsKey(user)) {
      likedUsers.put(user, 0);
    }
  }

  /**
   *
   * @param user that this user will check against.
   *
   * @return true if the user has liked the other user sufficient times.
   */

  public boolean checkIfMatch(User user) {
    if (haveLikedUser(user) && user.haveLikedUser(this) && !matches.contains(user.getEmail())) {
      matches.add(user.getEmail());
      user.matches.add(this.getEmail());
    }
    return haveLikedUser(user) && user.haveLikedUser(this);
  }

  /**
   *
   * @param user that this user will check against
   *
   * @return true if the user has liked the other user more than 3 times
   *
   */

  public boolean haveLikedUser(User user) {
    if (!likedUsers.containsKey(user)) {
      return false;
    }
    return likedUsers.get(user) >= 3;
  }

  public int getImageHashCode() {
    return this.email.hashCode();
  }

}
