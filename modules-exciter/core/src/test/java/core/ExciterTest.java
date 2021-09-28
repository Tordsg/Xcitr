package core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ExciterTest {

    private Exciter exciter;

    @BeforeEach
    public void setUp() {
        exciter = new Exciter();
    }

    @Test
    public void likeFirst(){
        exciter.getNextUsers();
        exciter.pressedLikeFirst();
        Assertions.assertTrue(exciter.getCurrentUser().getAlreadyMatched().containsKey(exciter.getOnScreenUsers().get(0).getEmail()));
        //Assertions.assertTrue(exciter.getOnScreenUsers().get(0).getAlreadyMatched().containsKey(exciter.getCurrentUser().getEmail()));
    }

    @Test
    public void testMatch(){
        exciter.getNextUsers();
        for(int i = 0; i <3;i++){
            exciter.pressedLikeSecond();
        }
        Assertions.assertTrue(exciter.pressedLikeSecond());
    }
}

