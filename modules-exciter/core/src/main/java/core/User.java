package core;

import java.util.HashMap;

public class User implements MatchListener {

    private String name;
    private int age;
    private HashMap<User, Integer> likedByCounter = new HashMap<>();

    public User(String name, int age) {
        this.name = name;
        this.age = age;
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

    public void setAge(int age) {
        if (age < 0) {
            throw new IllegalArgumentException("Age cannot be negative");
        }
        this.age = age;
    }

    @Override
    public void fireOnMatch(User match) {
        if (likedByCounter.containsKey(match)) {
            likedByCounter.put(match, likedByCounter.get(match) + 1);
        } else {
            likedByCounter.put(match, 1);
        }
    }
}
