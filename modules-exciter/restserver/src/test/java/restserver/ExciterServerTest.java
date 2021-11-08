package restserver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
import json.FileHandler;
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
    FileHandler fileHandler = new FileHandler();
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
        Request requets = new Request.Builder().url("http://localhost:" + port).build();

        try {
            Response response = client.newCall(requets).execute();
            Assertions.assertEquals(200, response.code());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetUser() {
        User getUser = new User("test", 22, "testnr2@mail");
        getUser.setId(UUID.randomUUID());
        exciter.addUser(getUser);
        fileHandler.saveUser(exciter.getAllUsers());
        Request requets = new Request.Builder().url("http://localhost:" + port + "/user")
                .header("Authorization", getUser.getId().toString()).build();
        User newUser = null;
        try {
            ResponseBody response = client.newCall(requets).execute().body();
            newUser = mapper.readValue(response.string(), User.class);
        } catch (Exception e) {
        }
        Assertions.assertEquals(getUser.getName(), newUser.getName());
    }

    @Test
    public void testPostCreateUser() {
        Request request = null;
        Response response = null;
        ResponseBody responseBody = null;
        String responseBodyString = null;
        User newUser = new User("Nottest", 22, "Nottest@mail");
        String password = User.MD5Hash("test");
        try {
            String sendString = mapper.writeValueAsString(user);
            MediaType mediaType = MediaType.parse("application/json");
            request = new Request.Builder().url("http://localhost:" + port + "/createAccount").header("Pass", password)
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
        Assertions.assertNotNull(newUser.getId());
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
        addUser.setId(UUID.randomUUID());
        addUser.setPassword("test");
        String password = User.MD5Hash("test");
        exciter.addUser(addUser);
        try {
            String sendString = mapper.writeValueAsString(password);
            MediaType mediaType = MediaType.parse("application/json");
            request = new Request.Builder().url("http://localhost:" + port + "/login")
                    .header("mail", addUser.getEmail()).post(RequestBody.create(sendString, mediaType)).build();
            response = client.newCall(request).execute();
            responseBody = response.body();
            responseBodyString = responseBody.string();
            newUser = mapper.readValue(responseBodyString, User.class);

        } catch (IOException e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(addUser.getName(), newUser.getName());
        Assertions.assertEquals(addUser.getId(), newUser.getId());
        Assertions.assertTrue(response.isSuccessful());
    }

    @Test
    public void testUpdateUserInfo() {
        Request request = null;
        Response response = null;
        ResponseBody responseBody = null;
        String responseBodyString = null;
        User updatedUser = new User("Oliver", 22, "test@mail");
        updatedUser.setId(UUID.randomUUID());
        exciter.addUser(updatedUser);
        User newUser = null;
        User newUser2 = null;
        try {
            String sendString = mapper.writeValueAsString(updatedUser);
            MediaType mediaType = MediaType.parse("application/json");
            request = new Request.Builder().url("http://localhost:" + port + "/user/update")
                    .header("Authorization", updatedUser.getId().toString())
                    .post(RequestBody.create(sendString, mediaType)).build();
            response = client.newCall(request).execute();
            responseBody = response.body();
            responseBodyString = responseBody.string();
            newUser = mapper.readValue(responseBodyString, User.class);
            updatedUser.setUserInformation("likes response code 200");
            sendString = mapper.writeValueAsString(updatedUser);
            request = new Request.Builder().url("http://localhost:" + port + "/user/update")
                    .header("Authorization", updatedUser.getId().toString())
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
        testUser.setId(UUID.randomUUID());
        testUser.addMatch("Diana@mail");
        testUser.addMatch("Jane@mail");
        exciter.addUser(testUser);
        Request requets = new Request.Builder().url("http://localhost:" + port + "/user/matches")
                .header("Authorization", testUser.getId().toString()).build();
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

    @Test
    public void emulateMatch() {
        BotUser botuser1 = new BotUser("Bot", 18, "Boten@mail", true);
        BotUser botuser2 = new BotUser("Botto", 18, "Botto@mail", false);
        exciter.addUsers(List.of(botuser1, botuser2));
        user.setId(UUID.randomUUID());
        exciter.addUser(user);
        Response response = null;
        List<User> matchesFromServer = new ArrayList<>();
        try {
            MediaType mediaType = MediaType.parse("application/json");
            String sendString = mapper.writeValueAsString(List.of(botuser1, botuser2));
            for (int i = 0; i < 3; i++) {
                Request request = new Request.Builder().url("http://localhost:" + port + "/like")
                        .header("Authorization", user.getId().toString())
                        .post(RequestBody.create(sendString, mediaType)).build();
                response = client.newCall(request).execute();
            }
            Request request = new Request.Builder().url("http://localhost:" + port + "/user/matches")
                    .header("Authorization", user.getId().toString()).build();
            ResponseBody responseBody = client.newCall(request).execute().body();
            matchesFromServer = mapper.readValue(responseBody.string(),
                    mapper.getTypeFactory().constructCollectionType(List.class, User.class));

        } catch (IOException e) {
        }
        Assertions.assertEquals(200, response.code());
        Assertions.assertTrue(matchesFromServer.get(0).getEmail().equals(botuser1.getEmail()));
    }

    @Test
    public void getTwoUsers() {
        user.setId(UUID.randomUUID());
        exciter.addUser(user);
        Request request = null;
        Response response = null;
        List<User> users = new ArrayList<>();
        try {
            request = new Request.Builder().url("http://localhost:" + port + "/two")
                    .header("Authorization", user.getId().toString()).build();
            response = client.newCall(request).execute();
            users = mapper.readValue(response.body().string(),
                    mapper.getTypeFactory().constructCollectionType(List.class, User.class));
        } catch (Exception e) {
            // TODO: handle exception
        }
        Assertions.assertNotNull(users.get(0).getEmail());
        Assertions.assertNotNull(users.get(1).getEmail());
    }

    @Test
    public void testGetLike() {
        user.setId(UUID.randomUUID());
        BotUser botuser = new BotUser("Bot", 18, "Boten@mail", true);
        Integer testInt = null;
        exciter.addUsers(List.of(user, botuser));
        exciter.likePerson(user, botuser);
        Response response = null;
        try {
            Request request = new Request.Builder().url("http://localhost:" + port + "/user/likes")
                    .header("mail", botuser.getEmail())
                    .header("Authorization", user.getId().toString())
                    .build();
            response = client.newCall(request).execute();
            ResponseBody responseBody = response.body();
            testInt = mapper.readValue(responseBody.string(), Integer.class);
        } catch (IOException e) {

        }
        Assertions.assertTrue(response.code() == 200);
        Assertions.assertEquals(1, testInt);
    }

    @Test
    public void getNewUserFromList(){
        User user1 = new User("Ludde", 19, "Ludde@mail");
        User user2 = new User("Ludde", 19, "Ludde2@mail");
        User user3 = new User("Ludde", 19, "Ludd3@mail");
        User user4 = new User("Ludde", 19, "Ludd4@mail");
        user1.setId(UUID.randomUUID());
        user2.setId(UUID.randomUUID());
        user3.setId(UUID.randomUUID());
        user4.setId(UUID.randomUUID());
        exciter.addUsers(List.of(user1, user2, user3, user4));
        MediaType mediaType = MediaType.parse("application/json");
        List<User> users = List.of(user2, user3, user4);
        Response response = null;
        User returnUser = null;
        try {
            String sendString = mapper.writeValueAsString(users);
            Request request = new Request.Builder().url("http://localhost:" + port + "/user/new")
                    .header("Authorization", user1.getId().toString())
                    .post(RequestBody.create(sendString, mediaType)).build();
            response = client.newCall(request).execute();
            ResponseBody responseBody = response.body();
            returnUser = mapper.readValue(responseBody.string(), User.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(200, response.code());
        Assertions.assertNotNull(returnUser);
        Assertions.assertNotEquals(user1.getEmail(), returnUser.getEmail());
        Assertions.assertNotEquals(user2.getEmail(), returnUser.getEmail());
        Assertions.assertNotEquals(user3.getEmail(), returnUser.getEmail());
        Assertions.assertNotEquals(user4.getEmail(), returnUser.getEmail());
    }

}
