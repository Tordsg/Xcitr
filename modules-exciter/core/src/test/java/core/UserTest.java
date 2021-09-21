package core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserTest {

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User("Roger",22,"Rytmisk sportsgymnastikk");
    }

    @Test
    public void testUser() {
        Assertions.assertEquals("Roger", user.getName());
        Assertions.assertEquals(22, user.getAge());
        Assertions.assertEquals("Rytmisk sportsgymnastikk", user.getUserInformation());
    }

    @Test
    public void testExceptions() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> user.setAge(-2));
    }

}
