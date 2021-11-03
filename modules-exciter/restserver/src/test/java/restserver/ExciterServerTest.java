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

    @Test
    public void testConnection() {
        Request requets = new Request.Builder().url("http://localhost:" + port + "/user").build();

        try {
            Response response = client.newCall(requets).execute();
            System.out.println(response.body().string());
            Assertions.assertEquals(200, response.code());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetUser() {
        exciter.setCurrentUser(user);
        Request requets = new Request.Builder().url("http://localhost:" + port + "/user").build();
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
        Assertions.assertEquals(newUser.getName(), exciter.getCurrentUser().getName());
        Assertions.assertTrue(response.isSuccessful());
    }

    @Test
    public void testPostCreateUserFail() {
        exciter.setCurrentUser(user);
        Request request = null;
        Response response = null;
        ResponseBody responseBody = null;
        String responseBodyString = null;
        User newUser = new User("test", 22, "test@mail");
        try {
            String sendString = mapper.writeValueAsString(user);
            MediaType mediaType = MediaType.parse("application/json");
            request = new Request.Builder().url("http://localhost:" + port + "/createAccount")
                    .post(RequestBody.create(sendString, mediaType)).build();
            response = client.newCall(request).execute();
            responseBody = response.body();
            responseBodyString = responseBody.string();
            // mapper should give out errors
            newUser = mapper.readValue(responseBodyString, User.class);

        } catch (IOException e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(400, response.code());
        Assertions.assertNotEquals(newUser, exciter.getCurrentUser());

    }

    @Test
    public void testPostLogin() {
        Request request = null;
        Response response = null;
        ResponseBody responseBody = null;
        String responseBodyString = null;
        User newUser = null;
        User addUser = new User("Per", 17, "Per@mail");
        addUser.setPassword("test");
        String password = User.MD5Hash("test");
        List<User> usersToAdd = new ArrayList<User>();
        usersToAdd.add(addUser);
        exciter.addUsers(usersToAdd);
        try {
            String sendString = mapper.writeValueAsString(password);
            MediaType mediaType = MediaType.parse("application/json");
            request = new Request.Builder().url("http://localhost:" + port + "/login/Per@mail").post(RequestBody.create(sendString,mediaType)).build();
            response = client.newCall(request).execute();
            responseBody = response.body();
            responseBodyString = responseBody.string();
            System.out.println(responseBodyString);
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
        exciter.setCurrentUser(updatedUser);
        User newUser = null;
        User newUser2 = null;
        try {
            String sendString = mapper.writeValueAsString(updatedUser);
            MediaType mediaType = MediaType.parse("application/json");
            request = new Request.Builder().url("http://localhost:" + port + "/user")
                    .post(RequestBody.create(sendString, mediaType)).build();
            response = client.newCall(request).execute();
            responseBody = response.body();
            responseBodyString = responseBody.string();
            newUser = mapper.readValue(responseBodyString, User.class);
            updatedUser.setUserInformation("likes response code 200");
            sendString = mapper.writeValueAsString(updatedUser);
            request = new Request.Builder().url("http://localhost:" + port + "/user")
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
        exciter.setCurrentUser(testUser);
        Request requets = new Request.Builder().url("http://localhost:" + port + "/user/matches").build();
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

    // @Test
    // public void testUserPatch() {
    //     try {
    //         Request request = new Request.Builder().url("http://localhost:" + port + "/user/patch").build();
    //         Response response = client.newCall(request).execute();
    //         Assertions.assertEquals(200, response.code());
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }
    // }

}
