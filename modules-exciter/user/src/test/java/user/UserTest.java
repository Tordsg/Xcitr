package user;

import java.util.Arrays;
import java.util.HashMap;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserTest {

    private User user;
    private BotUser botUser;

    @BeforeEach
    public void setUp() {
        user = new User("Roger",22,"roger@mail");
        botUser = new BotUser("Tonje",22,"bowling","tonje@mail",true,1);
    }

    @Test
    public void testUser() {
        Assertions.assertEquals("Roger", user.getName());
        Assertions.assertEquals(22, user.getAge());
        Assertions.assertEquals("roger@mail", user.getEmail());
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
}
