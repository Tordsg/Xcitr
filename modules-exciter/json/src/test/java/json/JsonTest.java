package json;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import core.*;

public class JsonTest {

    FileHandler fileHandler = new FileHandler();
    private User user;
    private Exciter exciter;

    @BeforeEach
    public void setUp() {
        exciter = new Exciter();
        fileHandler.createFile();
        user = new User("Ola Nordmann", 26, "Fiskesprett på søndager","ola@mail");
        exciter.setCurrentUser(user);

    }

    @Test
    public void readFromFile(){
        fileHandler.saveUser(user);
        User userReadFromFile = fileHandler.readUser();
        Assertions.assertNotEquals("Ola Nordmann", userReadFromFile.getName());
        Assertions.assertNotEquals(26, userReadFromFile.getAge());
        Assertions.assertNotEquals("ola@mail", userReadFromFile.getEmail());
    }

    @Test
    public void readMatches(){
        for (int i = 0; i < 3; i++) {
            exciter.pressedLikeFirst();
        }
        User onScreenUser1 = exciter.getOnScreenUser1();
        fileHandler.saveUser(user);
        Assertions.assertTrue(exciter.pressedLikeFirst());
        User userReadFromFile = fileHandler.readUser();
        Assertions.assertNotEquals(userReadFromFile.getAlreadyMatched(), user.getAlreadyMatched());
        fileHandler.saveUser(user);
        userReadFromFile = fileHandler.readUser();
        Assertions.assertEquals(userReadFromFile.getAlreadyMatched(), user.getAlreadyMatched());
        Assertions.assertTrue(userReadFromFile.getAlreadyMatched().containsKey(onScreenUser1.getEmail()));

    }

}

