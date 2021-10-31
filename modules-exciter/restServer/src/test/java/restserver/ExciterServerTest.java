package restserver;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import core.Exciter;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import user.User;

@SpringBootTest(classes = ExciterApplication.class)
public class ExciterServerTest {

    OkHttpClient client = new OkHttpClient();
    ObjectMapper mapper = new ObjectMapper();
    Exciter exciter = ExciterApplication.excite;
    User user = new User("test", 22, "test@mail");

    @BeforeEach
    public void setup() {
        exciter.clearUser(user);
    }

    @Test
    public void testConnection() {
        Request requets = new Request.Builder().url("http://localhost:8080/user").build();
        try {
            Response response = client.newCall(requets).execute();
            Assertions.assertEquals(200, response.code());

        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    @Test
    public void testGetUser() {
        exciter.setCurrentUser(user);
        Request requets = new Request.Builder().url("http://localhost:8080/user").build();
        try {
            ResponseBody response = client.newCall(requets).execute().body();
            User newUser = mapper.readValue(response.string(), User.class);
            Assertions.assertEquals(user.getName(), newUser.getName());
        } catch (Exception e) {
        }
    }

    @Test
    public void testPostCreateUser()
    {
        try {
            String sendString = mapper.writeValueAsString(user);
            MediaType mediaType = MediaType.parse("application/json");
            Request requets = new Request.Builder().url("http://localhost:8080/createAccount").post(RequestBody.create(sendString, mediaType)).build();
            Response response = client.newCall(requets).execute();
            ResponseBody responseBody = response.body();
            Assertions.assertEquals(user.getName(), exciter.getCurrentUser().getName());
            Assertions.assertTrue(response.isSuccessful());
            Assertions.assertEquals(responseBody.string(), "true");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFalseCreateUser(){
        exciter.setCurrentUser(user);
        try {
            String sendString = mapper.writeValueAsString(user);
            MediaType mediaType = MediaType.parse("application/json");
            Request requets = new Request.Builder().url("http://localhost:8080/createAccount").post(RequestBody.create(sendString, mediaType)).build();
            Response response = client.newCall(requets).execute();
            ResponseBody responseBody = response.body();
            Assertions.assertNotEquals(user.getName(), exciter.getCurrentUser().getName());
            Assertions.assertTrue(response.isSuccessful());
            Assertions.assertEquals(responseBody.string(), "false");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
