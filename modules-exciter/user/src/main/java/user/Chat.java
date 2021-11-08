package user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = ChatDeserializer.class)
public class Chat {
    private String user1;
    private String user2;
    private List<HashMap<String, String>> messages = new ArrayList<HashMap<String, String>>();

    public Chat(String String1, String String2, List<HashMap<String, String>> messages) {
        this.user1 = String1;
        this.user2 = String2;
        this.messages = messages;
    }
    public Chat(String user1, String user2) {
        this.user1 = user1;
        this.user2 = user2;
    }

    public String getUser1() {
        return user1;
    }

    public String getUser2() {
        return user2;
    }

    public List<HashMap<String, String>> getMessages() {
        return messages;
    }

    public void sendMeesage(String user1, String message) {
        HashMap<String, String> messageMap = new HashMap<>();
        messageMap.put(user1, message);
        messages.add(messageMap);
    }
}
