package restserver;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import core.Exciter;
import json.FileHandler;
import json.MessageHandler;
import user.BotUser;
import user.Chat;
import user.User;

@RestController
public class ServerController {

    private Exciter excite = ExciterApplication.excite;
    private FileHandler fileHandler = new FileHandler();
    private MessageHandler messageHandler = new MessageHandler();

    @GetMapping("/")
    public String index() {
        return "Hello World! Welcome to Exciter";
    }

    @GetMapping(value = "/user")
    public User CurrentUser(@RequestHeader("Authorization") UUID id) {
        User user = excite.getUserById(id);
        if (user == null) {
            throw new IllegalArgumentException("User does not exist");
        }
        return user;
    }

    @PostMapping(value = "/createAccount")
    public User createAccount(@RequestBody User user, @RequestHeader("Pass") String pass) {
        if (excite.getUserByEmail(user.getEmail()) != null) {
            throw new IllegalArgumentException("User already exists");
        }
        user.setPasswordNoHash(pass);
        user.setId(UUID.randomUUID());
        excite.addUser(user);
        fileHandler.saveUser(excite.getAllUsers());
        return user;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(value = org.springframework.http.HttpStatus.BAD_REQUEST)
    public String handleIllegalArgumentException(IllegalArgumentException e) {
        return e.getMessage();
    }

    @GetMapping(value = "/user/matches")
    public List<User> getMatches(@RequestHeader("Authorization") UUID id) {
        List<User> matches = new ArrayList<>();
        User thisUser = excite.getUserById(id);
        List<String> matchesEmail = thisUser.getMatches();
        for (User user : excite.getAllUsers()) {
            if (matchesEmail.contains(user.getEmail())) {
                matches.add(user);
            }
        }
        return matches;
    }

    @PostMapping(value = "/login")
    public User setLoginUser(@RequestHeader("mail") String mail, @RequestBody String password) {
        User user = excite.getUserByEmail(mail);
        if (user != null) {
            if (user.getPassword().equals(password.replace("\"", ""))) {
                return excite.getUserByEmail(mail);
            } else {
                throw new IllegalArgumentException("Wrong password");
            }

        }
        throw new IllegalArgumentException("User does not exist");
    }

    @PostMapping(value = "/user/update")
    public User updateUserInfo(@RequestHeader("Authorization") UUID id, @RequestBody User user) {
        if (excite.getUserById(id) == null) {
            throw new IllegalAccessError("You do not have permission to update this user");
        }
        User thisUser = excite.getUserByEmail(user.getEmail());
        thisUser.setName(user.getName());
        thisUser.setAge(user.getAge());
        thisUser.setUserInformation(user.getUserInformation());
        fileHandler.saveUser(excite.getAllUsers());

        return thisUser;
    }

    @PostMapping(value = "/user/update/password")
    public User updateUserPassword(@RequestHeader("Authorization") UUID id, @RequestBody String password) {
        if (excite.getUserById(id) == null) {
            throw new IllegalAccessError("You do not have permission to update this user");
        }
        User thisUser = excite.getUserById(id);
        thisUser.setPasswordNoHash(password.replace("\"", ""));
        fileHandler.saveUser(excite.getAllUsers());
        return thisUser;
    }

    @ExceptionHandler(IllegalAccessError.class)
    @ResponseStatus(value = org.springframework.http.HttpStatus.FORBIDDEN)
    public String handleIllegalAccessError(IllegalAccessError e) {
        return e.getMessage();
    }

    @PostMapping(value = "/like")
    public User likeUser(@RequestHeader("Authorization") UUID id, @RequestBody List<User> users) {
        User thisUser = excite.getUserById(id);
        if (excite.getUserByEmail(users.get(0).getEmail()) == null
                || excite.getUserByEmail(users.get(1).getEmail()) == null
                || excite.getUserByEmail(thisUser.getEmail()) == null) {
            throw new IllegalArgumentException("User does not exist");
        }
        excite.likePerson(thisUser, excite.getUserByEmail(users.get(0).getEmail()));
        excite.resetLikes(thisUser, excite.getUserByEmail(users.get(1).getEmail()));
        List<User> tmp = List.of(thisUser, excite.getUserByEmail(users.get(0).getEmail()),
                excite.getUserByEmail(users.get(1).getEmail()));
        return excite.getNextRandomUser(tmp);

    }

    @GetMapping(value = "/two")
    public List<User> getTwoUsers(@RequestHeader("Authorization") UUID id) {
        User thisUser = excite.getUserById(id);
        if (thisUser == null) {
            throw new IllegalArgumentException("User does not exist");
        }
        return excite.getTwoUniqueUsers(thisUser);
    }

    @GetMapping(value = "/user/likes")
    public int getLikes(@RequestHeader("Authorization") UUID id, @RequestHeader("mail") String user) {
        User thisUser = excite.getUserById(id);
        User likeUser = excite.getUserByEmail(user);
        if (thisUser == null || likeUser == null) {
            throw new IllegalArgumentException("User does not exist");
        }
        if (thisUser.getLikedUsers().containsKey(likeUser.getEmail())) {
            return thisUser.getLikedUsers().get(likeUser.getEmail());
        }
        return 0;
    }

    @PostMapping(value = "/user/new")
    public User postMethodName(@RequestHeader("Authorization") UUID id, @RequestBody List<User> users) {
        User thisUser = excite.getUserById(id);
        List<String> list = users.stream().map(User::getEmail).collect(Collectors.toList());
        List<User> tmp = excite.getUsersFromList(list);
        tmp.add(thisUser);
        if (tmp.contains(null)) {
            throw new IllegalArgumentException("User does not exist");
        }
        User returnUser = excite.getNextRandomUser(tmp);
        return returnUser;
    }

    @PostMapping(value = "/message")
    public Chat sendChat(@RequestHeader("Authorization") UUID id,@RequestHeader("mail") String mail, @RequestBody String message) {
        User user = excite.getUserById(id);
        User user2 = excite.getUserByEmail(mail);
        Chat chat = messageHandler.getChat(user.getEmail(), mail);
        if(user == null || user2 == null){
            throw new IllegalArgumentException("User does not exist");
        }
        if (chat == null) {
            chat = new Chat(user.getEmail(), mail);
        }
        chat.sendMeesage(user.getEmail(), message);
        if(user2 instanceof BotUser) {
            chat.sendMeesage(mail, ((BotUser) user2).reply());
        }
        messageHandler.saveChat(chat);
        return chat;
    }

    @GetMapping(value = "/message")
    public Chat getChat(@RequestHeader("Authorization") UUID id, @RequestHeader("mail") String mail) {
        User user = excite.getUserById(id);
        User user2 = excite.getUserByEmail(mail);
        Chat chat = messageHandler.getChat(user.getEmail(), mail);
        if(user == null || user2 == null){
            throw new IllegalArgumentException("User or chat does not exist");
        }
        if(chat == null){
            chat = new Chat(user.getEmail(), mail);
        }
        return chat;
    }
}
