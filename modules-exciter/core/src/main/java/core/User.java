package core;

import java.util.HashMap;

public class User implements MatchListener {

    private String name;
    private int age;
    private String userInformation;
    private String email;
    private HashMap<String, Integer> likedByCounter = new HashMap<>();

    /**
     * Constructor for User class
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
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
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
        return userInformation;
    }

    public void setAge(int age) {
        if (age < 0) {
            throw new IllegalArgumentException("Age cannot be negative");
        }
        this.age = age;
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

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", userInformation='" + userInformation + '\'' +
                ", email='" + email + '\'' +
                ", likedByCounter=" + likedByCounter +
                '}';
    }

}
