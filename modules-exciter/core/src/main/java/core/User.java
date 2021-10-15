package core;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

public class User implements MatchListener {

    private String name;
    private int age;
    private String userInformation;
    private String email;
    private HashMap<String, Integer> likedByCounter = new HashMap<>();
    private String password = null;

    /**
     * Constructor for User class
     *
     * @param name
     * @param age
     * @param userInformation
     * @param likedByCounter
     * @param email
     * @param password
     *
     * @apiNote This constructor is to only be used by the filehandler class
     */
    public User(String name, int age, String userInformation, HashMap<String, Integer> likedByCounter, String email, String password) {
        this.userInformation = userInformation;
        this.likedByCounter = likedByCounter;
        this.name = name;
        this.email = email;
        this.password = password;
        setAge(age);
    }
    /**
     * Constructor for User class
     *
     * @param name
     * @param age
     * @param userInformation
     * @param likedByCounter
     * @param email
     */
    public User(String name, int age, String userInformation, HashMap<String, Integer> likedByCounter, String email) {
        this.userInformation = userInformation;
        this.likedByCounter = likedByCounter;
        this.name = name;
        this.email = email;
        setAge(age);
    }

    /**
     * Constructor for User class
     *
     * @param name
     * @param age
     * @param userInformation
     * @param email
     */
    public User(String name, int age, String userInformation, String email) {
        this.userInformation = userInformation;
        this.name = name;
        this.email = email;
        setAge(age);
    }

    /**
     * Constructor for User class
     *
     * @param name
     * @param age
     * @param email
     */
    public User(String name, int age, String email) {
        this.name = name;
        setAge(age);
        this.email = email;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return this.age;
    }

    public void setEmail(String email) {
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
        this.password = MD5Hash(password);;
    }

    /**
     * Security implementation with MD5
     */
    public static String MD5Hash(String password) {
        String outString = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
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

    public HashMap<String, Integer> getAlreadyMatched() {
        return new HashMap<>(likedByCounter);
    }

    @Override
    public void fireOnLike(User match) {
        this.addUserOnMatch(match);
    }

    public boolean containsPreviousMatch(User match) {
        return likedByCounter.containsKey(match.getEmail());
    }

    public void addUserOnMatch(User match) {
        if (!likedByCounter.containsKey(match.getEmail())) {
            likedByCounter.put(match.getEmail(), 1);
        } else {
            likedByCounter.put(match.getEmail(), likedByCounter.get(match.getEmail()) + 1);
        }
    }

    public void resetUserMatch(User user) {
        if (likedByCounter.containsKey(user.getEmail())) {
            likedByCounter.put(user.getEmail(), 0);
        }
    }

    @Override
    public boolean checkIfMatch(User user) {
        return haveLikedUser(user) && user.haveLikedUser(this);
    }

    public boolean haveLikedUser(User user) {
        if (!likedByCounter.containsKey(user.getEmail())) {
            return false;
        }
        return likedByCounter.get(user.getEmail()) >= 3;
    }

    public int getImageHashCode() {
        return this.email.hashCode();
    }

    @Override
    public String toString() {
        return "User{" + "name='" + name + '\'' + ", age=" + age + ", userInformation='" + userInformation + '\''
                + ", email='" + email + '\'' + ", likedByCounter=" + likedByCounter + '}';
    }

}
