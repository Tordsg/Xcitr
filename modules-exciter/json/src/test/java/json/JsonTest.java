package json;


import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import core.User;

public class JsonTest {

    FileHandler fileHandler = new FileHandler();
    private User user;

    @Before
    public void setUp() {
        fileHandler.createFile();
    }

    @Test
    public void readFromFile(){
        user = new User("Ola Nordmann", 26, "Fiskesprett på søndager","ola@mail");
        fileHandler.saveUser(user);
        User userReadFromFile = fileHandler.readUser();
        Assertions.assertEquals("Ola Nordmann", userReadFromFile.getName());
        Assertions.assertEquals(26, userReadFromFile.getAge());
        Assertions.assertEquals("ola@mail", userReadFromFile.getEmail());
    }

}

