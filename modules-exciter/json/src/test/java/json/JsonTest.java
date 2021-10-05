package json;


import org.junit.Before;
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
        Assertions.assertEquals("Ola Nordmann", userReadFromFile.getName());
        Assertions.assertEquals(26, userReadFromFile.getAge());
        Assertions.assertEquals("ola@mail", userReadFromFile.getEmail());
    }

    @Test
    public void readMatches(){
        User onScreenUser1 = exciter.getOnScreenUser1();
        for (int i = 0; i < 3; i++) {
            exciter.pressedLikeFirst();
        }
        fileHandler.saveUser(user);
        User userReadFromFile = fileHandler.readUser();
        Assertions.assertTrue(exciter.pressedLikeFirst());
    }

}

