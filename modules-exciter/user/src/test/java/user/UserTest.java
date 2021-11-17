package user;

import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for User class.
 */

public class UserTest {

    private User user;
    private BotUser botUser;

    /**
     * Sets up one user and one botuser before each test.
     */

    @BeforeEach
    public void setUp() {
        user = new User("Roger",22,"roger@mail.no");
        botUser = new BotUser("Tonje",22,"bowling","tonje@mail.no",true,1);
    }

    @Test
    public void testUser() {
        Assertions.assertEquals("Roger", user.getName());
        Assertions.assertEquals(22, user.getAge());
        Assertions.assertEquals("roger@mail.no", user.getEmail());
        Assertions.assertEquals("Tonje", botUser.getName());
        Assertions.assertEquals(22, botUser.getAge());
        Assertions.assertEquals("bowling", botUser.getUserInformation());
    }

    @Test
    public void testExceptions() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> user.setAge(-2));
        Assertions.assertThrows(IllegalArgumentException.class, () -> botUser.setAge(-2));
    }

    @Test
    public void testPassword() {
        user.setPassword("password");
        Assertions.assertEquals("5f4dcc3b5aa765d61d8327deb882cf99", user.getPassword());
        Assertions.assertNull(botUser.getPassword());
    }

    @Test
    public void testSimpleBot() {
        Assertions.assertTrue(botUser.isLikeBack());
    }

    @Test
    public void testIllegalName(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> user.setName("noe.test"));
    }

    @Test
    public void testIllegalEmail(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> user.setEmail("noe.mail"));
    }

    @Test
    public void testLikeUser(){
        user.fireOnLike(botUser.getEmail());
        user.fireOnLike(botUser.getEmail());
        user.fireOnLike(botUser.getEmail());
        HashMap<String, Integer> likedUser = new HashMap<>();
        likedUser.put(botUser.getEmail(), 3);
        Assertions.assertEquals(likedUser, user.getLikedUsers());

    }

    @Test
    public void testAddMatch(){
        user.addMatch(botUser.getEmail());
        Assertions.assertEquals(Arrays.asList(botUser.getEmail()), user.getMatches());
    }

    @Test
    public void testDifferentConstructors() {
        User user = new User(UUID.randomUUID(), "test", 2, "userInformation", null, "test.test@mail.com", "password", null, 2);
        Assertions.assertEquals("test", user.getName());
        Assertions.assertEquals(2, user.getAge());
        Assertions.assertEquals("userInformation", user.getUserInformation());
        Assertions.assertThrows(IllegalArgumentException.class , () -> user.setAge(-2));
        Assertions.assertThrows(IllegalArgumentException.class , () -> user.setEmail("test"));
        user.setPasswordNoHash("test");
        Assertions.assertEquals("test", user.getPassword());

        User user2 = new User(UUID.randomUUID(), "nytest", 22, "userInformation", null, "entest@mail.no", "password", null);
        Assertions.assertEquals("nytest", user2.getName());
        Assertions.assertEquals(22, user2.getAge());
        Assertions.assertEquals("userInformation", user2.getUserInformation());
        Assertions.assertThrows(IllegalArgumentException.class , () -> user2.setAge(-2));
        Assertions.assertThrows(IllegalArgumentException.class , () -> user2.setEmail("test@mail"));

        User user3 = new User("testtre", 22, "userInformation", null, "mail@mymail.com", "password");
        Assertions.assertEquals("testtre", user3.getName());
        Assertions.assertEquals(22, user3.getAge());
        Assertions.assertEquals("userInformation", user3.getUserInformation());
        Assertions.assertThrows(IllegalArgumentException.class , () -> user3.setAge(-2));
        Assertions.assertThrows(IllegalArgumentException.class , () -> user3.setEmail("test@mail"));

        User user4 = new User("testFire", 22, "userInformation", null, "testfire@mail.no");
        Assertions.assertEquals("testFire", user4.getName());
        Assertions.assertEquals(22, user4.getAge());
        Assertions.assertEquals("userInformation", user4.getUserInformation());
    }

    @Test
    public void testMatchFalse(){
        User user = new User("jon", 22, "jon@mail.no");
        User user2 = new User("silje", 20, "silje@mail.no");
        Assertions.assertFalse(user.checkIfMatch(user2));
        Assertions.assertFalse(user.getMatches().contains(user2.getEmail()));
        user.addMatch(user2.getEmail());
        user2.addMatch(user.getEmail());
        Assertions.assertTrue(user.getMatches().contains(user2.getEmail()));
    }

    @Test
    public void resetMatchCounter() {
        user.fireOnLike(botUser.getEmail());
        user.fireOnLike(botUser.getEmail());
        user.fireOnLike(botUser.getEmail());
        botUser.fireOnLike(user.getEmail());
        botUser.fireOnLike(user.getEmail());
        botUser.fireOnLike(user.getEmail());
        Assertions.assertTrue(user.checkIfMatch(botUser));
        user.resetUserMatch(botUser.getEmail());
        Assertions.assertEquals(0, user.getLikedUsers().get(botUser.getEmail()));
        user.fireOnLike(botUser.getEmail());
        user.fireOnLike(botUser.getEmail());
        user.fireOnLike(botUser.getEmail());
        user.resetUserMatchToOne(botUser.getEmail());
        Assertions.assertEquals(1, user.getLikedUsers().get(botUser.getEmail()));
    }
}
