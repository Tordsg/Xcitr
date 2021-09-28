package core;

import java.util.HashMap;

public class User implements MatchListener {

    private String name;
    private int age;
    private String userInformation;
    private HashMap<User, Integer> likedByCounter = new HashMap<>();

    /**
     * Constructor
     *
     * @param name
     * @param age
     * @param userInformation
     */
    public User(String name, int age, String userInformation) {
        this.name = name;
        setAge(age);
        this.userInformation = userInformation;
    }

    public User(String name, int age) {
        this.name = name;
        setAge(age);
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

    public HashMap<User, Integer> getAlreadyMatched() {
        return new HashMap<>(likedByCounter);
    }

    @Override
    public void fireOnLike(User match) {
        match.addUserOnMatch(this);
    }

    public boolean containsPreviousMatch(User match) {
        return likedByCounter.containsKey(match);
    }

    public void addUserOnMatch(User match) {
        if (!likedByCounter.containsKey(match)) {
            likedByCounter.put(match, 1);
        } else {
            likedByCounter.put(match, likedByCounter.get(match) + 1);
        }
    }

    @Override
    public boolean checkIfMatch(User user) {
        return haveLikedUser(user) && user.haveLikedUser(this);
    }

    public boolean haveLikedUser(User user) {
        if (!likedByCounter.containsKey(user)) {
            return false;
        }
        return likedByCounter.get(user) >= 3;
    }
}
