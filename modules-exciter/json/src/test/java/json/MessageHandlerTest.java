package json;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import user.Chat;
import user.User;

public class MessageHandlerTest {
    private User user1 = new User("user", 22, "user@mail.no");
    private User user2 = new User("userto", 22, "userto@mail.no");
    private Chat chat = new Chat(user1.getEmail(), user2.getEmail());
    private MessageHandler messageHandler = new MessageHandler();

    @BeforeEach
    public void setUp() {
        user1 = new User("user", 22, "user@mail.no");
        user2 = new User("userto", 22, "userto@mail.no");
        chat = new Chat(user1.getEmail(), user2.getEmail());
    }

    @Test
    public void testsendMessage() {
        chat.sendMeesage(user1.getEmail(), "Hello");
        chat.sendMeesage(user2.getEmail(), "Hello");
        chat.sendMeesage(user1.getEmail(), "how are you?");
        chat.sendMeesage(user2.getEmail(), "I am fine");

        messageHandler.saveChat(chat);
        Chat chat2 = messageHandler.getChat(user1.getEmail(), user2.getEmail());
        Assertions.assertEquals(chat.getMessages(), chat2.getMessages());
    }

    @Test
    public void testDuplicate() {
        chat.sendMeesage(user1.getEmail(), "Hello");
        chat.sendMeesage(user2.getEmail(), "Hello");
        chat.sendMeesage(user1.getEmail(), "how are you?");
        chat.sendMeesage(user2.getEmail(), "I am fine");

        messageHandler.saveChat(chat);
        messageHandler.saveChat(chat);
        Chat chat2 = messageHandler.getChat(user1.getEmail(), user2.getEmail());
        Assertions.assertEquals(chat.getMessages(), chat2.getMessages());
    }

    @Test
    public void testAddedChats() {
        chat.sendMeesage(user1.getEmail(), "Hello");
        chat.sendMeesage(user2.getEmail(), "Hello");
        chat.sendMeesage(user1.getEmail(), "how are you?");
        chat.sendMeesage(user2.getEmail(), "I am fine");

        messageHandler.saveChat(chat);
        chat.sendMeesage(user1.getEmail(), "message");
        messageHandler.saveChat(chat);
        Chat chat2 = messageHandler.getChats().get(0);
        Assertions.assertNotEquals(chat.getMessages(), chat2.getMessages());
    }

    @Test
    public void testNewChat() {
        User user3 = new User("user", 22, "usertre@mail.no");
        User user4 = new User("userto", 22, "userfire@mail.no");
        Chat chat3 = new Chat(user3.getEmail(), user4.getEmail());
        chat3.sendMeesage(user3.getEmail(), "Hello");
        chat3.sendMeesage(user4.getEmail(), "Hello");
        messageHandler.saveChat(chat3);
        Chat chat2 = messageHandler.getChat(user3.getEmail(), user4.getEmail());
        Assertions.assertEquals(chat3.getMessages(), chat2.getMessages());
    }

}
