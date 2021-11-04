package restserver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @GetMapping(value = "/user/{mail}")
    public @ResponseBody User CurrentUser(@PathVariable("mail") String mail) {
        return fileHandler.getUser(mail);
    }

    @GetMapping(value = "/onScreenUsers")
    public @ResponseBody ArrayList<User> getOnScreenUsers() {
        return excite.getOnScreenUsers();
    }

    @RequestMapping(value = "/{userMail}/onScreenUsers/{mail}")
    @ResponseBody
    public User discardUser(@PathVariable("userMail") String usermail, @PathVariable("mail") ) {



   
    }

    @PostMapping(value = "/createAccount")
    public User createAccount(@RequestBody User user) {
        if (fileHandler.getUser(user.getEmail()) != null) {
            throw new IllegalArgumentException("User already exists");
        }
        fileHandler.saveUser(Arrays.asList(user));
        return user;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(value = org.springframework.http.HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String handleIllegalArgumentException(IllegalArgumentException e) {
        return e.getMessage();
    }

    @GetMapping(value = "/user/matches/{mail}")
    public @ResponseBody List<User> getMatches(@PathVariable("mail") String email) {
        List<User> matches = new ArrayList<>();
        User thisUser = fileHandler.getUser(email);
        List<String> matchesEmail = thisUser.getMatches();
        for (User user : fileHandler.readUsers()) {
            if(matchesEmail.contains(user.getEmail())){
                matches.add(user);
            }
         }
        return matches;
    }

    @PostMapping(value = "/login/{mail}")
    @ResponseBody
    public User setLoginUser(@PathVariable("mail") String email, @RequestBody String password) {
        if (excite.getUserByEmail(email) != null) {
            if (excite.getUserByEmail(email).getPassword().equals(password.replace("\"", ""))) {
                return fileHandler.getUser(email);
            } else {
                throw new IllegalArgumentException("Wrong password");
            }

        }
        throw new IllegalArgumentException("User does not exist");
    }

    @PostMapping(value = "/user/{mail}")
    public User updateUserInfo(@PathVariable("mail") User user) {
        User thisUser = fileHandler.getUser(user.getEmail());
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

    @PostMapping(value = "/discard")
    @ResponseBody
    public User discardUser(@RequestBody User user) {
        if (user.getEmail().equals(excite.getOnScreenUser1().getEmail())) {
            excite.discardFirst();
            return excite.getOnScreenUser1();
        } else if (user.getEmail().equals(excite.getOnScreenUser2().getEmail())) {
            excite.discardSecond();
            return excite.getOnScreenUser2();
        } else {
            throw new IllegalArgumentException(
                    String.format("User %s is not not avaliable at this moment", user.getEmail()));
        }
    }
}
