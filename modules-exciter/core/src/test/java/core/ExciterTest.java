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
        exciter.pressedLikeFirst();
        Assertions.assertTrue(exciter.getCurrentUser().getLikedUsers().containsKey(exciter.getOnScreenUsers().get(0).getEmail()));
    }

    @Test
    public void testMatch(){
        exciter.getNextUsers();
        exciter.setOnScreenUser2(botUser);
        for(int i = 0; i <3;i++){
            exciter.pressedLikeSecond();
        }
        Assertions.assertTrue(exciter.pressedLikeSecond());
        Assertions.assertTrue(exciter.getOnScreenUser2().getLikedUsers().containsKey(exciter.getCurrentUser().getEmail()));
    }

    @Test
    public void resetOnScreenUsers(){
        User user = exciter.getOnScreenUser1();
        User user2 = exciter.getOnScreenUser2();
        exciter.refreshUsers();
        Assertions.assertFalse(user.equals(exciter.getOnScreenUser1()) || user2.equals(exciter.getOnScreenUser2()));
    }

}

