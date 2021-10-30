package restServer;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.GetMapping;
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
    public ArrayList<User> getOnScreenUsers(){
        return excite.getOnScreenUsers();
    }

    @GetMapping(value ="/hei")
    public String hei(){
        return "hei";
    }

   /* @Deprecated
    }
*/
  /*
@RestController
public class GreetingController {
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping(value="/greeting")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }
*/
}

