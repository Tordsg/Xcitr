package restserver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import core.Exciter;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import user.BotUser;
import user.User;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { ExciterApplication.class, ServerController.class })
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ExciterServerTest {

    OkHttpClient client = new OkHttpClient();
    ObjectMapper mapper = new ObjectMapper();
    Exciter exciter = ExciterApplication.excite;
    User user = new User("test", 22, "test@mail");

    @LocalServerPort
    int port;

    @BeforeEach
    public void setup() {
        user = new User("test", 22, "test@mail");
        exciter.clearUser(user);
    }

    // @Test
    // public void testConnection() {
    // Request requets = new Request.Builder().url("http://localhost:" + port +
    // "/user").build();

    // try {
    // Response response = client.newCall(requets).execute();
    // Assertions.assertEquals(200, response.code());

    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // }

    @Test
    public void testGetUser() {
        exciter.addUsers(List.of(user));
        Request requets = new Request.Builder().url("http://localhost:" + port + "/user")
                .header("Authorization", "Bearer: " + user.getEmail()).build();
        User newUser = null;
        try {
            ResponseBody response = client.newCall(requets).execute().body();
            newUser = mapper.readValue(response.string(), User.class);
        } catch (Exception e) {
        }
        Assertions.assertEquals(user.getName(), newUser.getName());
    }

    @Test
    public void testPostCreateUser() {
        Request request = null;
        Response response = null;
        ResponseBody responseBody = null;
        String responseBodyString = null;
        User newUser = new User("Nottest", 22, "Nottest@mail");
        try {
            String sendString = mapper.writeValueAsString(user);
            MediaType mediaType = MediaType.parse("application/json");
            request = new Request.Builder().url("http://localhost:" + port + "/createAccount")
                    .post(RequestBody.create(sendString, mediaType)).build();
            response = client.newCall(request).execute();
            responseBody = response.body();
            responseBodyString = responseBody.string();
            newUser = mapper.readValue(responseBodyString, User.class);

        } catch (IOException e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(newUser.getName(), user.getName());
        Assertions.assertTrue(response.isSuccessful());
    }

    @Test
    public void testPostCreateUserFail() {
        exciter.addUser(user);
        Request request = null;
        Response response = null;
        try {
            String sendString = mapper.writeValueAsString(user);
            MediaType mediaType = MediaType.parse("application/json");
            request = new Request.Builder().url("http://localhost:" + port + "/createAccount")
                    .post(RequestBody.create(sendString, mediaType)).build();
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(400, response.code());
    }

    @Test
    public void testPostLogin() {
        Request request = null;
        Response response = null;
        ResponseBody responseBody = null;
        String responseBodyString = null;
        User newUser = new User("NotPer", 17, "NotPer@mail");
        User addUser = new User("Per", 17, "Per@mail");
        addUser.setPassword("test");
        String password = User.MD5Hash("test");
        exciter.addUser(addUser);
        try {
            String sendString = mapper.writeValueAsString(password);
            MediaType mediaType = MediaType.parse("application/json");
            request = new Request.Builder().url("http://localhost:" + port + "/login")
                    .header("Authorization", "Bearer: " + addUser.getEmail())
                    .post(RequestBody.create(sendString, mediaType)).build();
            response = client.newCall(request).execute();
            responseBody = response.body();
            responseBodyString = responseBody.string();
            newUser = mapper.readValue(responseBodyString, User.class);

        } catch (IOException e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(addUser.getName(), newUser.getName());
        Assertions.assertTrue(response.isSuccessful());
    }

    @Test
    public void testUpdateUserInfo() {
        Request request = null;
        Response response = null;
        ResponseBody responseBody = null;
        String responseBodyString = null;
        User updatedUser = new User("Oliver", 22, "test@mail");
        exciter.addUser(updatedUser);
        User newUser = null;
        User newUser2 = null;
        try {
            String sendString = mapper.writeValueAsString(updatedUser);
            MediaType mediaType = MediaType.parse("application/json");
            request = new Request.Builder().url("http://localhost:" + port + "/user/update")
                    .header("Authorization", "Bearer: " + updatedUser.getEmail())
                    .post(RequestBody.create(sendString, mediaType)).build();
            response = client.newCall(request).execute();
            responseBody = response.body();
            responseBodyString = responseBody.string();
            newUser = mapper.readValue(responseBodyString, User.class);
            updatedUser.setUserInformation("likes response code 200");
            sendString = mapper.writeValueAsString(updatedUser);
            request = new Request.Builder().url("http://localhost:" + port + "/user/update")
                    .header("Authorization", "Bearer: " + updatedUser.getEmail())
                    .post(RequestBody.create(sendString, mediaType)).build();
            response = client.newCall(request).execute();
            responseBody = response.body();
            responseBodyString = responseBody.string();
            newUser2 = mapper.readValue(responseBodyString, User.class);

        } catch (IOException e) {
            e.printStackTrace();
        }
        Assertions.assertNull(newUser.getUserInformation());
        Assertions.assertNotEquals(newUser.getUserInformation(), newUser2.getUserInformation());
        Assertions.assertEquals("likes response code 200", newUser2.getUserInformation());
    }

    @Test
    public void testGetMatches() {
        User testUser = new User("Ludde", 19, "Ludde@mail");
        testUser.addMatch("Diana@mail");
        testUser.addMatch("Jane@mail");
        exciter.addUser(testUser);
        Request requets = new Request.Builder().url("http://localhost:" + port + "/user/matches")
                .header("Authorization", "Bearer: " + testUser.getEmail()).build();
        List<User> matchesFromServer = new ArrayList<>();
        try {
            ResponseBody response = client.newCall(requets).execute().body();
            matchesFromServer = mapper.readValue(response.string(),
                    mapper.getTypeFactory().constructCollectionType(List.class, User.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (User user : matchesFromServer) {
            Assertions.assertTrue(testUser.getMatches().contains(user.getEmail()));
        }
    }

}
