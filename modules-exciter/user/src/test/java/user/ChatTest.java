package user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class ChatTest {

    User user1 = new User("user", 22, "user@mail.no");
    User user2 = new User("userTo", 22, "user2@mail.no");
    Chat chat = new Chat(user1.getEmail(), user2.getEmail());

    @BeforeEach
    public void setUp() {
        user1 = new User("user", 22, "user@mail.no");
        user2 = new User("userTo", 22, "user2@mail.no");
        chat = new Chat(user1.getEmail(), user2.getEmail());
    }

    @Test
    public void testSendMessage(){
        chat.sendMeesage(user1.getEmail(), "Hej");
        chat.sendMeesage(user2.getEmail(), "Hej");
        Assertions.assertEquals(chat.getMessages().size(), 2);
    }

    @Test
    public void getMessage() {
        chat.sendMeesage(user1.getEmail(), "Hej");
        chat.sendMeesage(user2.getEmail(), "Hej");
        Assertions.assertEquals(chat.getMessages().get(0).get(chat.getUser1()), "Hej");
        Assertions.assertEquals(chat.getMessages().get(1).get(chat.getUser2()), "Hej");
    }

    @Test
    public void getMessageWrongOrder() {
        chat.sendMeesage(user1.getEmail(), "Hej");
        chat.sendMeesage(user2.getEmail(), "Hej");
        Assertions.assertNotEquals(chat.getMessages().get(1).get(user1.getEmail()), "Hej");
        Assertions.assertNotEquals(chat.getMessages().get(0).get(user2.getEmail()), "Hej");
    }

    @Test
    public void testSetMessages() {
        Map<String, String> map = new HashMap<>();
        List<Map<String, String>> list = new ArrayList<>();
        map.put(user1.getEmail(), "Hej");
        list.add(map);
        map.clear();
        map.put(user2.getEmail(), "Hej");
        list.add(map);
        chat.setMessages(list);
        Assertions.assertEquals(chat.getMessages().size(), 2);
    }

    @Test
    public void testLargeConstructor()
    {
        Chat chat = new Chat(user1.getEmail(), user2.getEmail(), List.of(Map.of(
                user1.getEmail(), "Hej"), Map.of(user2.getEmail(), "Hej")));
        Assertions.assertEquals(chat.getMessages().size(), 2);
    }

    @Test
    public void testSetUsers(){
        chat.setUser1(new User("ny", 22, "ny@mail.com").getEmail());
        chat.setUser2(new User("ny", 22, "nyTo@mail.com").getEmail());
        Assertions.assertEquals(chat.getUser1(), "ny@mail.com");
        Assertions.assertEquals(chat.getUser2(), "nyTo@mail.com");
    }
}
