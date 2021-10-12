package core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserTest {

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User("Roger",22,"roger@mail");
    }

    @Test
    public void testUser() {
        Assertions.assertEquals("Roger", user.getName());
        Assertions.assertEquals(22, user.getAge());
        Assertions.assertEquals("roger@mail", user.getEmail());
        Assertions.assertEquals(user.getEmail().hashCode(), user.getImageHashCode());
    }

    @Test
    public void testExceptions() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> user.setAge(-2));
    }

    @Test
    public void testPassword() {
        user.setPassword("password");
        Assertions.assertEquals("5f4dcc3b5aa765d61d8327deb882cf99", user.getPassword());
    }

}
