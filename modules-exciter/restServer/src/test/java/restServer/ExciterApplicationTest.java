package restServer;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

//import okhttp3.Request;




@SpringBootTest
class ExciterApplicationTest {

	@Test
    public void testGetUser() {
        //Request request = new Request.Builder().url("http://localhost:8080" + "/hei").build();
        Assertions.assertTrue(true);
    }

}