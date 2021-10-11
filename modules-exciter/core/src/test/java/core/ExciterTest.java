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
        Assertions.assertTrue(exciter.getCurrentUser().getAlreadyMatched().containsKey(exciter.getOnScreenUsers().get(0).getEmail()));
    }

    @Test
    public void testMatch(){
        exciter.getNextUsers();
        exciter.setOnScreenUser2(botUser);
        for(int i = 0; i <3;i++){
            exciter.pressedLikeSecond();
        }
        Assertions.assertTrue(exciter.pressedLikeSecond());
        Assertions.assertTrue(exciter.getOnScreenUser2().getAlreadyMatched().containsKey(exciter.getCurrentUser().getEmail()));
    }

}

