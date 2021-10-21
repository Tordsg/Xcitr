package core;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ExciterTest {

    private Exciter exciter;
    private BotUser botUser = new BotUser("Sofie", 23, "sofie@mail", true);

    @BeforeEach
    public void setUp() {
        exciter = new Exciter();
    }

    @Test
    public void likeFirst() {
        exciter.getNextUsers();
        exciter.discardSecond();
        Assertions.assertTrue(exciter.getCurrentUser().getLikedUsers().containsKey(exciter.getOnScreenUsers().get(0)));
    }

    @Test
    public void testMatch() {
        exciter.getNextUsers();
        exciter.setOnScreenUser2(botUser);
        for (int i = 0; i < 2; i++) {
            exciter.discardFirst();
        }
        Assertions.assertTrue(exciter.discardFirst());
        Assertions.assertEquals("sofie@mail", exciter.getCurrentUserMatches().get(0));
    }

    @Test
    public void resetOnScreenUsers() {
        User user = exciter.getOnScreenUser1();
        User user2 = exciter.getOnScreenUser2();
        exciter.refreshUsers();
        Assertions.assertFalse(user.equals(exciter.getOnScreenUser1()) || user2.equals(exciter.getOnScreenUser2()));
    }

    @Test
    public void testChangeCurrentUser() {
        final User testUser = new User("Roger", 25, "Roger@mail");
        User testUser2 = exciter.getAllUsers().stream().filter(u -> u.getEmail().equals(testUser.getEmail()))
                .findFirst().orElse(null);
        exciter.setCurrentUser(testUser2);
        Assertions.assertEquals(testUser2, exciter.getCurrentUser());
        Assertions.assertFalse(exciter.getAllUsers().contains(testUser2));
    }

    @Test
    public void testAddUsers() {
        User testUser = new User("Roger", 25, "RogerNew@mail");
        User testUser2 = new User("Bob", 25, "Bob@mail");
        User testUser3 = new User("Rolf", 25, "Rolf@mail");
        User testUser4 = new User("Bjarne", 25, "Bjarne@mail");
        User testUser5 = new User("Unni", 25, "Unni@mail");
        User testUser6 = new User("Roger", 25, "RogerNew@mail"); // duplicate should not be added

        List<User> testUsers = List.of(testUser, testUser2, testUser3, testUser4, testUser5, testUser6);
        int count = exciter.getAllUsers().size();
        exciter.addUsers(testUsers);
        Assertions.assertEquals(count + 5, exciter.getAllUsers().size());
    }

}
