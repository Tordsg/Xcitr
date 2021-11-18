package user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * This class configure a user.
 */
@JsonDeserialize(using = UserDeserializer.class)
public class User {

  private UUID id;
  private String name;
  private int age;
  private String userInformation;
  private String email;
  private Map<String, Integer> likedUsers = new HashMap<>();
  private List<String> matches = new ArrayList<>();
  @JsonIgnore
  private String password = null;
  private int imageId = 0;  
  
  /**
   * Constructor for User class.
   *
   * @param name string of users name
   * @param age int of users age
   * @param email string of users email
   */

  public User(String name, int age, String email) {
    setName(name);
    setAge(age);
    setEmail(email);
  }

  /**
   * Constructor for User class.
   *
   * @param id users app ID
   * @param name string of users name
   * @param age users age
   * @param userInformation string of user bio
   * @param matches list of the users matches
   * @param email string of users email
   * @param password string of  users password
   * @param likedUsers map of the user and how many times they´ve liked other users
   * @param imageId string of users chosen image´s id
   * @apiNote This constructor is to only be used by the userhandler class.
   */

  public User(UUID id, String name, int age, 
      String userInformation, List<String> matches, String email,
      String password, Map<String, Integer> likedUsers, int imageId) {
    this(id, name, age, userInformation, matches, email, password, likedUsers);
    this.imageId = imageId;
  }

  /**
   * Constructor for User class.
   *
   * @param id users app ID
   * @param name string of user name
   * @param age users age
   * @param userInformation string of user bio
   * @param matches list of matches the user has
   * @param email string of users email
   * @param password string of users password
   * @param likedUsers map of the user and how many times they´ve liked other users
   * @apiNote This constructor is to only be used by the userhandler class.
   */

  public User(UUID id, String name, int age, 
      String userInformation, List<String> matches, String email,
      String password, Map<String, Integer> likedUsers) {
    this(name, age, userInformation, matches, email, password);
    setId(id);
    this.likedUsers = likedUsers;
  }

  /**
   * Constructor for User class.
   *
   * @param name string of user name
   * @param age int of users age
   * @param userInformation string of the users bio
   * @param matches list of the users matches
   * @param email string of the users email
   * @param password string of the users password
   * @apiNote This constructor is to only be used by the userhandler class.
   */

  public User(String name, int age, String userInformation, 
      List<String> matches, String email, String password) {
    this(name, age, userInformation, matches, email);
    this.password = password;
  }

  /**
   * Constructor for User class.
   *
   * @param name string of users name
   * @param age int of users age
   * @param userInformation string of users bio
   * @param matches list of users matches
   * @param email string of users email
   */

  public User(String name, int age, String userInformation, List<String> matches, String email) {
    this(name, age, userInformation, email);
    this.matches = matches;
  }

  /**
   * Constructor for User class.
   *
   * @param name string of users name
   * @param age int of users age
   * @param userInformation string of users bio
   * @param email string of users email
   */

  public User(String name, int age, String userInformation, String email) {
    this(name, age, email);
    setUserInformation(userInformation);
  }

  public User() {
  }

  public String getName() {
    return this.name;
  }

  private boolean checkForLetters(String name) {
    for (int i = 0; i < name.length(); i++) {
      if (!(Character.isLetter(name.charAt(i)) || name.charAt(i) == ' ' || name.charAt(i) == '-')) {
        return false;
      }
    }
    return true;
  }

  /**
   * Setting the users name.
   *
   * @param name string for the users name
   */

  public void setName(String name) {
    if (name.length() < 2) {
      throw new IllegalArgumentException("Name must be at least 2 characters");
    }
    if (name.length() > 20) {
      throw new IllegalArgumentException("Name cannot be longer than 20 characters");
    }
    if (!checkForLetters(name)) {
      throw new IllegalArgumentException("Name must only contain letters");
    }
    this.name = name;

  }

  public void setId(UUID id) {
    this.id = id;
  }

  public UUID getId() {
    return id;
  }

  public int getAge() {
    return this.age;
  }

  /**
   * Setting the users email.
   *
   * @param email string for the users email
   */
  public void setEmail(String email) {
    if (!emailValidator(email)) {
      throw new IllegalArgumentException("Email is not valid");
    }
    this.email = email;
  }

  /**
   * Validates email.
   *
   * @param email email to validate
   * @return true if email is valid
   */
  private boolean emailValidator(String email) {
    String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." 
        + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
        + "A-Z]{2,7}$";

    java.util.regex.Pattern pat = java.util.regex.Pattern.compile(emailRegex);
    java.util.regex.Matcher mat = pat.matcher(email);
    return mat.matches();
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

  /**
   * Setting the age of a user.
   *
   * @param age int for the users age
   */
  public void setAge(int age) {
    if (age < 0) {
      throw new IllegalArgumentException("Age cannot be negative");
    }
    this.age = age;
  }

  public void setPassword(String password) {
    this.password = md5Hash(password);
  }

  public void setPasswordNoHash(String password) {
    this.password = password;
  }

  /**
   * This method is used to hash the password.
   *
   * @param password string
   * @return MD5 hash of password
   */

  public static String md5Hash(String password) {
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

  public void setLikedUsers(Map<String, Integer> likedUsers) {
    this.likedUsers = likedUsers;
  }

  public Map<String, Integer> getLikedUsers() {
    return new HashMap<>(likedUsers);
  }

  public void addMatch(String matches) {
    this.matches.add(matches);
  }

  public List<String> getMatches() {
    return new ArrayList<>(matches);
  }

  public boolean containsPreviousMatch(String match) {
    return likedUsers.containsKey(match);
  }

  /**
   * Adds a user to the likedUsers HashMap or updates likecount with 1.
   *
   * @param match The user to be added or likecount updated
   *
   */

  public void fireOnLike(String match) {
    if (!likedUsers.containsKey(match)) {
      likedUsers.put(match, 1);
    } else {
      likedUsers.put(match, likedUsers.get(match) + 1);
    }
  }

  /**
   * If a user is liked sufficiently, like count resets to 0.
   */

  public void resetUserMatch(String email) {
    if (likedUsers.containsKey(email)) {
      likedUsers.put(email, 0);
    }
  }

  /** 
   * Resets the user match to one.
   *
   * @param email string for the users email 
   */
  public void resetUserMatchToOne(String email) {
    if (likedUsers.containsKey(email)) {
      likedUsers.put(email, 1);
    }
  }

  /**
   * Checks if there is a match between two users.
   *
   * @param user the user that will be check if it is a match
   *
   * @return true if the user has liked the other user sufficient times
   */

  public boolean checkIfMatch(User user) {
    if (haveLikedUser(user.getEmail()) 
        && user.haveLikedUser(this.getEmail()) && !matches.contains(user.getEmail())) {
      matches.add(user.getEmail());
      user.matches.add(this.getEmail());
    }
    return haveLikedUser(user.getEmail()) && user.haveLikedUser(this.getEmail());
  }

  /**
   * Checks whether a user has liked someone three times.
   *
   * @param email that this user will check against
   *
   * @return true if the user has liked the other user more than 3 times
   */

  public boolean haveLikedUser(String email) {
    if (!likedUsers.containsKey(email)) {
      return false;
    }
    return likedUsers.get(email) >= 3;
  }

  /**
   * Setting the image id for the users images.  
   *
   * @param i the image id integer
   */
  public void setImageId(int i) {
    if (i < 0 || i > 25) {
      throw new IllegalArgumentException("Image id must be between 0 and 24");
    }
    this.imageId = i;
  }

  public int getImageId() {
    return this.imageId;
  }

}
