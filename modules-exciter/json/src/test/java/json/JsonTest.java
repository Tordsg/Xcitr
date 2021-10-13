package json;


import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import core.*;

public class JsonTest {

    FileHandler fileHandler = new FileHandler();
    private User user;
    private Exciter exciter;
    private ArrayList<User> users = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        users.clear();
        exciter = new Exciter();
        fileHandler.createFile();
        user = new User("Ola Nordmann", 26, "Fiskesprett på søndager","ola@mail");
        exciter.setCurrentUser(user);
        users.add(user);
    }

    @Test
    public void readFromFile(){
        fileHandler.saveUser(users);
        ArrayList<User> userReadFromFile = fileHandler.readUsers();
        Assertions.assertEquals("Ola Nordmann", userReadFromFile.get(0).getName());
        Assertions.assertEquals(26, userReadFromFile.get(0).getAge());
        Assertions.assertEquals("ola@mail", userReadFromFile.get(0).getEmail());
    }

    @Test
    public void readMatches(){
        for (int i = 0; i < 3; i++) {
            exciter.pressedLikeFirst();
        }
        User onScreenUser1 = exciter.getOnScreenUser1();
        fileHandler.saveUser(users);
        User userReadFromFile = fileHandler.readUsers().get(0);
        userReadFromFile = fileHandler.readUsers().get(0);
        Assertions.assertEquals(userReadFromFile.getAlreadyMatched(), user.getAlreadyMatched());
        Assertions.assertTrue(userReadFromFile.getAlreadyMatched().containsKey(onScreenUser1.getEmail()));

    }

    @Test
    public void getExplicitUser() {
        fileHandler.saveUser(users);
        Assertions.assertEquals(users.get(0).getName(), fileHandler.getUser("ola@mail").getName());
        Assertions.assertEquals(users.get(0).getAge(), fileHandler.getUser("ola@mail").getAge());
        Assertions.assertEquals(users.get(0).getEmail(), fileHandler.getUser("ola@mail").getEmail());
        Assertions.assertEquals(users.get(0).getUserInformation(), fileHandler.getUser("ola@mail").getUserInformation());
    }

    @Test
    public void testPasswords(){
        users.add(new BotUser("bot", 24, "bot@mail", true));
        users.get(0).setPassword("password");
        fileHandler.saveUser(users);
        Assertions.assertEquals("5f4dcc3b5aa765d61d8327deb882cf99", fileHandler.getUser("ola@mail").getPassword());
        Assertions.assertNull(fileHandler.getUser("bot@mail").getPassword());
    }

    @Test
    public void testNullfind(){
        Assertions.assertNull(fileHandler.getUser("404notfound@mail"));
        //First checks while file is empty
        //Second checks when file is not empty but does not contain the user
        fileHandler.saveUser(users);
        Assertions.assertNull(fileHandler.getUser("404notfound@mail"));
    }

}

