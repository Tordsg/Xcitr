package restserver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @GetMapping(value = "/user")
    public @ResponseBody User CurrentUser(@RequestHeader("Authorization") String auth) {
        User user = fileHandler.getUser(auth.split(" ")[1]);
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
        excite.addUser(user);
        fileHandler.saveUser(Arrays.asList(user));
        return user;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(value = org.springframework.http.HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String handleIllegalArgumentException(IllegalArgumentException e) {
        return e.getMessage();
    }

    @GetMapping(value = "/user/matches")
    public @ResponseBody List<User> getMatches(@RequestHeader("Authorization") String auth) {
        List<User> matches = new ArrayList<>();
        User thisUser = excite.getUserByEmail(auth.split(" ")[1]);
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
    public User setLoginUser(@RequestHeader("Authorization") String auth, @RequestBody String password) {
        User user = excite.getUserByEmail(auth.split(" ")[1]);
        if (user != null) {
            if (user.getPassword().equals(password.replace("\"", ""))) {
                return excite.getUserByEmail(auth.split(" ")[1]);
            } else {
                throw new IllegalArgumentException("Wrong password");
            }

        }
        throw new IllegalArgumentException("User does not exist");
    }

    @PostMapping(value = "/user/update")
    public User updateUserInfo(@RequestHeader("Authorization") String auth, @RequestBody User user) {
        if(!auth.split(" ")[1].equals(user.getEmail())) {
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

}
