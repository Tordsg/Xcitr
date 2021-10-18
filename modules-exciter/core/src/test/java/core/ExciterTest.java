package core;

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
    public void likeFirst(){
        exciter.getNextUsers();
        exciter.discardSecond();
        Assertions.assertTrue(exciter.getCurrentUser().getLikedUsers().containsKey(exciter.getOnScreenUsers().get(0)));
    }

    @Test
    public void testMatch(){
        exciter.getNextUsers();
        exciter.setOnScreenUser2(botUser);
        for(int i = 0; i < 3;i++){
            exciter.discardFirst();
        }
        Assertions.assertTrue(exciter.discardFirst());
        Assertions.assertTrue(exciter.getCurrentUser().getMatches().contains(exciter.getOnScreenUser2().getEmail()));
        Assertions.assertEquals("sofie@mail", exciter.getCurrentUser().getMatches().get(0));
    }

    @Test
    public void resetOnScreenUsers(){
        User user = exciter.getOnScreenUser1();
        User user2 = exciter.getOnScreenUser2();
        exciter.refreshUsers();
        Assertions.assertFalse(user.equals(exciter.getOnScreenUser1()) || user2.equals(exciter.getOnScreenUser2()));
    }

}

