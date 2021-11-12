package user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = ChatDeserializer.class)
public class Chat {
    private String user1;
    private String user2;
    private List<Map<String, String>> messages = new ArrayList<>();

    public Chat(String String1, String String2, List<Map<String, String>> messages) {
        this.user1 = String1;
        this.user2 = String2;
        this.messages = messages;
    }
    public Chat(String user1, String user2) {
        this.user1 = user1;
        this.user2 = user2;
    }
    public Chat() {
    }

    public void setUser1(String user1) {
        this.user1 = user1;
    }

    public void setUser2(String user2) {
        this.user2 = user2;
    }

    public void setMessages(List<Map<String, String>> messages) {
        this.messages = messages;
    }

    public String getUser1() {
        return user1;
    }

    public String getUser2() {
        return user2;
    }

    public List<Map<String, String>> getMessages() {
        return messages;
    }

    public void sendMeesage(String user, String message) {
        Map<String, String> messageMap = new HashMap<>();
        messageMap.put(user, message);
        messages.add(messageMap);
    }
}