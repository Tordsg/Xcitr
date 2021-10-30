package restServer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import core.Exciter;
import user.User;


@RestController
public class ServerController {

    private Exciter excite = ExciterApplication.excite;

    @GetMapping(value ="/user")
    public @ResponseBody User CurrentUser(){
        return excite.getCurrentUser();
    }

    @GetMapping(value ="/onScreenUsers")
    public @ResponseBody ArrayList<User> getOnScreenUsers(){
        return excite.getOnScreenUsers();
    }

    @PostMapping(value ="/onScreenUsers")
    public int likedOnScreenUser1(){
        excite.getCurrentUser().fireOnLike(excite.getOnScreenUser1());
        return excite.getOnScreenUserLikeCount(excite.getOnScreenUser1());
    }
    //This method makes app crash
    // @PostMapping(value ="/onScreenUsers")
    // public int likedOnScreenUser2(){
    //     excite.getCurrentUser().fireOnLike(excite.getOnScreenUser2());
    //     return excite.getOnScreenUserLikeCount(excite.getOnScreenUser2());
    // }

    @GetMapping(value ="/matches")
    public @ResponseBody List<String> getMatches(){
        return excite.getCurrentUserMatches();
    }

    @PostMapping(value ="/matches")
    public List<String> updateMatches(@RequestBody User match){
        excite.getCurrentUser().addUserOnMatch(match);
        return excite.getCurrentUserMatches();
    }

    @GetMapping(value ="/hei")
    public String hei(){
        return "hei";
    }

    @PostMapping(value = "/signup")
    public User createUser(@RequestBody User newUser){
        excite.setCurrentUser(newUser);
        return excite.getCurrentUser();
    }

    @PostMapping(value = "/login")
    public User setLoginUser(@RequestBody String email){
        for (User user : excite.getAllUsers()) {
            if(user.getEmail().equals(email)){
                excite.setCurrentUser(user);
            }
        }
        return excite.getCurrentUser();

    }

    @PostMapping(value="/user")
    public User updateUserInfo(@RequestBody User currrentUser){
        return excite.getCurrentUser();
    }
}

