package restserver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.assertj.core.util.Arrays;
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
            // TODO: handle exception
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
    public void testPostCreateUser()
    {
        user.addMatch("Fiona@mail");
        Request request = null;
        Response response = null;
        ResponseBody responseBody = null;
        String responseBodyString = null;
        User newUser = new User("Nottest", 22, "Nottest@mail");
        try {
            String sendString = mapper.writeValueAsString(user);
            MediaType mediaType = MediaType.parse("application/json");
            request = new Request.Builder().url("http://localhost:"+port+"/createAccount").post(RequestBody.create(sendString, mediaType)).build();
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
    public void testPostCreateUserFail(){
        exciter.setCurrentUser(user);
        Request request = null;
        Response response = null;
        ResponseBody responseBody = null;
        String responseBodyString = null;
        User newUser = new User("test", 22, "test@mail");
        try {
            String sendString = mapper.writeValueAsString(user);
            MediaType mediaType = MediaType.parse("application/json");
            request = new Request.Builder().url("http://localhost:"+port+"/createAccount").post(RequestBody.create(sendString, mediaType)).build();
            response = client.newCall(request).execute();
            responseBody = response.body();
            responseBodyString = responseBody.string();
            newUser = mapper.readValue(responseBodyString, User.class);

        } catch (IOException e) {
            e.printStackTrace();
        }
        //TODO this method should not be allowed
        Assertions.assertTrue(responseBodyString.isEmpty());

    }

    @Test
    public void testPostLogin(){
        Request request = null;
        Response response = null;
        ResponseBody responseBody = null;
        String responseBodyString = null;
        User newUser = null;
        User addUser = new User("Per", 17, "Per@mail");
        List<User> usersToAdd = new ArrayList<User>();
        usersToAdd.add(addUser);
        exciter.addUsers(usersToAdd);
        try {
            String sendString = "Per@mail";
            MediaType mediaType = MediaType.parse("application/json");
            request = new Request.Builder().url("http://localhost:"+port+"/login").post(RequestBody.create(sendString, mediaType)).build();
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
    public void testUpdateUserInfo(){
        Request request = null;
        Response response = null;
        ResponseBody responseBody = null;
        String responseBodyString = null;
        User updatedUser = new User("Oliver", 22, "test@mail");
        exciter.setCurrentUser(user);
        User newUser = null;
        try {
            String sendString = mapper.writeValueAsString(updatedUser);
            MediaType mediaType = MediaType.parse("application/json");
            request = new Request.Builder().url("http://localhost:"+port+"/user").post(RequestBody.create(sendString, mediaType)).build();
            response = client.newCall(request).execute();
            responseBody = response.body();
            responseBodyString = responseBody.string();
            newUser = mapper.readValue(responseBodyString, User.class);

        } catch (IOException e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(updatedUser.getName(), newUser.getName());
        Assertions.assertTrue(response.isSuccessful());

    }
    @Test
    public void testGetMatches(){
        List<String> matches = new ArrayList<String>();
        matches.add("Mari");
        matches.add("Lisa");
        User testUser = new User("Ludde", 19, "Hei", matches, "Ludde@mail");
        exciter.setCurrentUser(testUser);
        Request requets = new Request.Builder().url("http://localhost:" + port + "/matches").build();
        List<String> matchesFromServer = null;
        try {
            ResponseBody response = client.newCall(requets).execute().body();
            matchesFromServer = mapper.readValue(response.string(), List.class);
        } catch (Exception e) {
        }
        Assertions.assertEquals(testUser.getMatches(), matchesFromServer);

    }

}
