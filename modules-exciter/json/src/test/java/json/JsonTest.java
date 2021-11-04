package json;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import core.*;
import user.User;
import user.BotUser;

public class JsonTest {

    FileHandler fileHandler = new FileHandler();
    private User user;
    private Exciter exciter;
    private List<User> users = new ArrayList<>();
    private BotUser botUser = new BotUser("lars", 22, "lars@mail", true);

    @BeforeEach
    public void setUp() {
        users.clear();
        exciter = new Exciter();
        fileHandler.createFile();
        fileHandler.saveUser(exciter.getAllUsers());
        user = new User("Ola Nordmann", 26, "Fiskesprett på søndager", "ola@mail");
        users.add(user);
    }

    @Test
    public void readFromFile() {
        fileHandler.saveUser(users);
        List<User> userReadFromFile = fileHandler.readUsers();
        Assertions.assertEquals("Ola Nordmann", userReadFromFile.get(0).getName());
        Assertions.assertEquals(26, userReadFromFile.get(0).getAge());
        Assertions.assertEquals("ola@mail", userReadFromFile.get(0).getEmail());
    }

    @Test
    public void readMatches() {
        for (int i = 0; i < 3; i++) {
            exciter.likePerson(user, botUser);
        }
        fileHandler.saveUser(users);
        User userReadFromFile = fileHandler.readUsers().get(0);
        Assertions.assertEquals(userReadFromFile.getMatches(), user.getMatches());
        Assertions.assertTrue(userReadFromFile.getMatches().contains(botUser.getEmail()));

    }

    @Test
    public void getExplicitUser() {
        fileHandler.saveUser(users);
        Assertions.assertEquals(users.get(0).getName(), fileHandler.getUser("ola@mail").getName());
        Assertions.assertEquals(users.get(0).getAge(), fileHandler.getUser("ola@mail").getAge());
        Assertions.assertEquals(users.get(0).getEmail(), fileHandler.getUser("ola@mail").getEmail());
        Assertions.assertEquals(users.get(0).getUserInformation(),
                fileHandler.getUser("ola@mail").getUserInformation());
    }

    @Test
    public void testPasswords() {
        users.add(new BotUser("bot", 24, "bot@mail", true));
        users.get(0).setPassword("password");
        fileHandler.saveUser(users);
        Assertions.assertEquals("5f4dcc3b5aa765d61d8327deb882cf99", fileHandler.getUser("ola@mail").getPassword());
        Assertions.assertNull(fileHandler.getUser("bot@mail").getPassword());
    }

    @Test
    public void testNullfind() {
        Assertions.assertNull(fileHandler.getUser("404notfound@mail"));
        // First checks while file is empty
        // Second checks when file is not empty but does not contain the user
        fileHandler.saveUser(users);
        Assertions.assertNull(fileHandler.getUser("404notfound@mail"));
    }

}
