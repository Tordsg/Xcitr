package restserver;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @PostMapping(value = "/onScreenUsers/{mail}")
    public boolean discardUser(@RequestParam("mail") String mail) {
        if (excite.getOnScreenUser1().equals(excite.getUserByEmail(mail))) {
            return excite.discardFirst();
        } else if (excite.getOnScreenUser2().equals(excite.getUserByEmail(mail))) {
            return excite.discardSecond();
        } else {
            return false;
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
    // This method makes app crash
    // @PostMapping(value ="/onScreenUsers")
    // public int likedOnScreenUser2(){
    // excite.getCurrentUser().fireOnLike(excite.getOnScreenUser2());
    // return excite.getOnScreenUserLikeCount(excite.getOnScreenUser2());
    // }

    @GetMapping(value = "/matches")
    public @ResponseBody List<String> getMatches() {
        return excite.getCurrentUserMatches();
    }


    @RequestMapping(value = "/login/{mail}/{password}")
    @ResponseBody
    public User setLoginUser(@PathVariable("mail") String email, @PathVariable("password") String password) {
        if (excite.getUserByEmail(email) != null) {
            if (excite.getUserByEmail(email).getPassword().equals(password)) {
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
        excite.setCurrentUser(user);
        return excite.getCurrentUser();
    }
}
