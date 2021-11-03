package restserver;

import java.util.ArrayList;
import java.util.List;

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
import user.User;

@RestController
public class ServerController {

    private Exciter excite = ExciterApplication.excite;

    @GetMapping(value = "/user")
    public @ResponseBody User CurrentUser() {
        return excite.getCurrentUser();
    }

    @GetMapping(value = "/onScreenUsers")
    public @ResponseBody ArrayList<User> getOnScreenUsers() {
        return excite.getOnScreenUsers();
    }

    @RequestMapping(value = "/onScreenUsers/{mail}")
    @ResponseBody
    public User discardUser(@PathVariable("mail") String mail) {
        if (excite.getOnScreenUser1().equals(excite.getUserByEmail(mail))) {
            return excite.getOnScreenUser1();
        } else if (excite.getOnScreenUser2().equals(excite.getUserByEmail(mail))) {
            return excite.getOnScreenUser2();
        } else {
            throw new IllegalArgumentException(String.format("User %s is not not avaliable at this moment", mail));
        }
    }

    @PostMapping(value = "/createAccount")
    public User createAccount(@RequestBody User user) {
        if (excite.getUserByEmail(user.getEmail()) != null) {
            throw new IllegalArgumentException("User already exists");
        }
        excite.setCurrentUser(user);
        return user;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(value = org.springframework.http.HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String handleIllegalArgumentException(IllegalArgumentException e) {
        return e.getMessage();
    }


    @GetMapping(value = "user/matches")
    public @ResponseBody List<User> getMatches() {
        List<User> matches = new ArrayList<>();
        for(String string : excite.getCurrentUser().getMatches()) {
            matches.add(excite.getUserByEmail(string));
        }
        return matches;
    }


    @PostMapping(value = "/login/{mail}")
    @ResponseBody
    public User setLoginUser(@PathVariable("mail") String email, @RequestBody String password) {
        if (excite.getUserByEmail(email) != null) {
            if (excite.getUserByEmail(email).getPassword().equals(password.replace("\"", ""))) {
                excite.setCurrentUser(excite.getUserByEmail(email));
                return excite.getCurrentUser();
            } else {
                throw new IllegalArgumentException("Wrong password");
            }

        }
        throw new IllegalArgumentException("User does not exist");
    }

    @PostMapping(value = "/user")
    public User updateUserInfo(@RequestBody User user) {
        if(!user.getEmail().equals(excite.getCurrentUser().getEmail())) {
            throw new IllegalAccessError("You are not allowed to change this user");
        }
        excite.getCurrentUser().setName(user.getName());
        if(user.getPassword() != null) {
            excite.getCurrentUser().setPassword(user.getPassword());
        }
        excite.getCurrentUser().setAge(user.getAge());
        excite.getCurrentUser().setUserInformation(user.getUserInformation());
        return excite.getCurrentUser();
    }

    @ExceptionHandler(IllegalAccessError.class)
    @ResponseStatus(value = org.springframework.http.HttpStatus.FORBIDDEN)
    @ResponseBody
    public String handleIllegalAccessError(IllegalAccessError e) {
        return e.getMessage();
    }
}
