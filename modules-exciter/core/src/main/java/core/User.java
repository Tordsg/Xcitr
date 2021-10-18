package core;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class User {

    private String name;
    private int age;
    private String userInformation;
    private String email;
    private HashMap<User, Integer> likedUsers = new HashMap<>();
    private List<String> matches = new ArrayList<>();
    private String password = null;

    /**
     * Constructor for User class
     *
     * @param name
     * @param age
     * @param userInformation
     * @param likedUsers
     * @param email
     * @param password
     *
     * @apiNote This constructor is to only be used by the filehandler class
     */
    public User(String name, int age, String userInformation, List<String> matches, String email, String password) {
        this.userInformation = userInformation;
        this.matches = matches;
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
     * @param likedUsers
     * @param email
     */
    public User(String name, int age, String userInformation, List<String> matches, String email) {
        this.userInformation = userInformation;
        this.matches = matches;
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

    //Security implementation with MD5
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

    public HashMap<User, Integer> getLikedUsers() {
        return new HashMap<>(likedUsers);
    }


    public void fireOnLike(User match) {
        this.addUserOnMatch(match);
    }

    public List<String> getMatches() {
        return new ArrayList<>(matches);
    }

    public boolean containsPreviousMatch(User match) {
        return likedUsers.containsKey(match);
    }

    public void addUserOnMatch(User match) {
        if (!likedUsers.containsKey(match)) {
            likedUsers.put(match, 1);
        } else {
            likedUsers.put(match, likedUsers.get(match) + 1);
        }
    }

    public void resetUserMatch(User user) {
        if (likedUsers.containsKey(user)) {
            likedUsers.put(user, 0);
        }
    }

    public boolean checkIfMatch(User user) {
        if (haveLikedUser(user) && user.haveLikedUser(this) && !matches.contains(user.getEmail())) {
            System.out.println("Match found");
            matches.add(user.getEmail());
            user.matches.add(this.getEmail());
        }
        return haveLikedUser(user) && user.haveLikedUser(this);
    }

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
