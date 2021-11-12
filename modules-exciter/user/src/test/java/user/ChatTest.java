package user;

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
        Assertions.assertEquals(chat.getMessages().get(0).get(user1.getEmail()), "Hej");
        Assertions.assertEquals(chat.getMessages().get(1).get(user2.getEmail()), "Hej");
    }

    @Test
    public void getMessageWrongOrder() {
        chat.sendMeesage(user1.getEmail(), "Hej");
        chat.sendMeesage(user2.getEmail(), "Hej");
        Assertions.assertNotEquals(chat.getMessages().get(1).get(user1.getEmail()), "Hej");
        Assertions.assertNotEquals(chat.getMessages().get(0).get(user2.getEmail()), "Hej");
    }
}
