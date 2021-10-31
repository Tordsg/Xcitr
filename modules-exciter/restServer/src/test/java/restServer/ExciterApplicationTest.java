package restServer;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;

import user.User;



@SpringBootTest
class ExciterApplicationTests {

	private User user;

	@Test
    public void testGetUser() throws IOException {

    }

}