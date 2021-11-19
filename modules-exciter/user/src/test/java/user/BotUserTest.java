package user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for BotUser class.
 */
public class BotUserTest {

    private BotUser botUser;
    private BotUser botUser2;

    /**
     * Makes two botusers before each test.
     */
    @BeforeEach
    public void setUp() {
        botUser = new BotUser("test", 22, "userInformation", "bot@mail.no", true, 1);
        botUser2 = new BotUser("testto", 20, "test2@mail.com", false);
    }

    @Test
    public void testReply() {
        Assertions.assertNotNull(botUser.reply());
    }

    @Test
    public void testConstructors() {
        Assertions.assertEquals("test", botUser.getName());
        Assertions.assertEquals("testto", botUser2.getName());
        Assertions.assertEquals(22, botUser.getAge());
        Assertions.assertEquals(20, botUser2.getAge());
        Assertions.assertEquals("userInformation", botUser.getUserInformation());
        Assertions.assertNull(botUser2.getUserInformation());
    }
}
