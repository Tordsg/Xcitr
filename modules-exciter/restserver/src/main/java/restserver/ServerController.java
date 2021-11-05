package restserver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import core.Exciter;
import json.FileHandler;
import user.User;

@RestController
public class ServerController {


    private Exciter excite = ExciterApplication.excite;
    private FileHandler fileHandler = new FileHandler();

    @GetMapping("/")
    public String index() {
        return "Hello World! Welcome to Exciter";
    }

    @GetMapping(value = "/user")
    public @ResponseBody User CurrentUser(@RequestHeader("Authorization") String id) {
        User user = fileHandler.getUserById(UUID.fromString(id.split(" ")[1]);
        if(user == null) {
            throw new IllegalArgumentException("User does not exist");
        }
        return user;
    }


    @PostMapping(value = "/createAccount")
    public User createAccount(@RequestBody User user) {
        if (excite.getUserByEmail(user.getEmail()) != null) {
            System.out.println("here");
            throw new IllegalArgumentException("User already exists");
        }
        user.setId(UUID.randomUUID());
        excite.addUser(user);
        List<User> tmp = excite.getAllUsers();
        tmp.add(user);
        fileHandler.saveUser(tmp);
        return user;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(value = org.springframework.http.HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String handleIllegalArgumentException(IllegalArgumentException e) {
        return e.getMessage();
    }

    @GetMapping(value = "/user/matches")
    public @ResponseBody List<User> getMatches(@RequestHeader("Authorization") String id) {
        List<User> matches = new ArrayList<>();
        User thisUser = fileHandler.getUserById(UUID.fromString(id.split(" ")[1]);
        List<String> matchesEmail = thisUser.getMatches();
        for (User user : excite.getAllUsers()) {
            if(matchesEmail.contains(user.getEmail())){
                matches.add(user);
            }
         }
        return matches;
    }

    @PostMapping(value = "/login")
    @ResponseBody
    public User setLoginUser(@RequestHeader("Authorization") String id, @RequestBody String password) {
        User user = fileHandler.getUserById(UUID.fromString(id.split(" ")[1]));
        if (user != null) {
            if (user.getPassword().equals(password.replace("\"", ""))) {
                return fileHandler.getUserById(UUID.fromString(id.split(" ")[1]));
            } else {
                throw new IllegalArgumentException("Wrong password");
            }

        }
        throw new IllegalArgumentException("User does not exist");
    }

    @PostMapping(value = "/user/update")
    public User updateUserInfo(@RequestHeader("Authorization") String id, @RequestBody User user) {
        if(!id.equals(user.getId().toString())) {
            throw new IllegalAccessError("You do not have permission to update this user");
        }
        User thisUser = excite.getUserByEmail(user.getEmail());
        thisUser.setName(user.getName());
        if (user.getPassword() != null) {
            thisUser.setPassword(user.getPassword());
        }
        thisUser.setAge(user.getAge());
        thisUser.setUserInformation(user.getUserInformation());

        return thisUser;
    }

    @ExceptionHandler(IllegalAccessError.class)
    @ResponseStatus(value = org.springframework.http.HttpStatus.FORBIDDEN)
    @ResponseBody
    public String handleIllegalAccessError(IllegalAccessError e) {
        return e.getMessage();
    }

    @PostMapping(value = "/like")
    @ResponseBody
    public User likeUser(@RequestHeader("Authorization") String id, @RequestBody List<User> users) {
        User thisUser = fileHandler.getUserById(UUID.fromString(id.split(" ")[1]));
        if(excite.getUserByEmail(users.get(0).getEmail()) == null ||
            excite.getUserByEmail(users.get(1).getEmail()) == null ||
            excite.getUserByEmail(thisUser.getEmail()) == null) {
            throw new IllegalArgumentException("User does not exist");
        }
        excite.likePerson(thisUser, excite.getUserByEmail(users.get(0).getEmail()));
        excite.resetLikes(thisUser, excite.getUserByEmail(users.get(1).getEmail()));
        List<User> tmp = List.of(thisUser,
                     excite.getUserByEmail(users.get(0).getEmail()),
                     excite.getUserByEmail(users.get(1).getEmail()));
        return excite.getNextRandomUser(tmp);

    }

}
