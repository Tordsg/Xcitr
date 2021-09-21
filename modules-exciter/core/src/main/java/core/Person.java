package core;

import java.util.List;
import java.util.ArrayList;

public class Person {

    private String name;
    private int age;
    private List<String> interests = new ArrayList<>();
    private String bio;

    public Person(String name, int age, List<String> interests, String bio){
        setName(name);
        setAge(age);
        setInterests(interests);
        setBio(bio);
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

    public void setAge(int age) {
        this.age = age;
    }

    public List<String> getInterests() {
        return this.interests;
    }

    public void setInterests(List<String> interests) {
        this.interests = interests;
    }

    public String getBio() {
        return this.bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void addInterest(String interest){
        interests.add(interest);
    }

    public void removeInterest(String interest){
        int index = 0;
        for (int i = 0; i < interests.size(); i++) {
            if(interests.get(i).equals(interest)){
                index = i;
            }
            
        }
        interests.remove(index);
    }

}
