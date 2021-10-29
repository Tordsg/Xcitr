package restServer;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import core.Exciter;
import user.User;

@RestController
public class ServerController {

    private final Exciter excite;

    ServerController(Exciter excite) {
        this.excite = excite;
    }

    @GetMapping(value ="/user")
    public User getCurrentUser(){
        return excite.getCurrentUser();
    }

    @GetMapping(value ="/onScreenUsers")
        public ArrayList<User> getOnScreenUsers(){
            return excite.getOnScreenUsers();
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

