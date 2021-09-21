package json;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import core.User;

public class JsonTest {

    FileHandler fileHandler = new FileHandler();
    private User user;

    @BeforeEach
    public void setUp() {
        user = new User("Ola Nordmann", 26, "Fiskesprett på søndager");
        //fileHandler.createFile();
        fileHandler.saveUser(user);
    }

    @Test
    public void readFromFile(){
        User userReadFromFile = fileHandler.readUser();
        Assertions.assertEquals("Ola Nordmann", userReadFromFile.getName());

    }

}

