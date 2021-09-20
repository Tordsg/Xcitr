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
        Assertions.assertTrue(exciter.getOnScreenUsers().get(0).getAlreadyMatched().containsKey(exciter.getCurrentUser()));
    }

    @Test
    public void testMatch(){
        exciter.getNextUsers();
        for(int i = 0; i <3;i++){
            exciter.getOnScreenUsers().get(1).fireOnLike(exciter.getCurrentUser());
            exciter.pressedLikeSecond();
        }
        Assertions.assertTrue(exciter.pressedLikeSecond());
        Assertions.assertFalse(exciter.pressedLikeFirst());
    }

}

