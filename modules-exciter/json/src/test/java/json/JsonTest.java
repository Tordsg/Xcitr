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
        fileHandler.createFile();
    }

    @Test
    public void readFromFile(){
        user = new User("Ola Nordmann", 26, "Fiskesprett på søndager");
        fileHandler.saveUser(user);
        User userReadFromFile = fileHandler.readUser();
        Assertions.assertEquals("Ola Nordmann", userReadFromFile.getName());
        Assertions.assertEquals(26, userReadFromFile.getAge());
        Assertions.assertEquals("Fiskesprett på søndager", userReadFromFile.getUserInformation());
    }

}

