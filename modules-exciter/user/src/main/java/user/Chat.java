package user;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Methods for the chat.
 */
@JsonDeserialize(using = ChatDeserializer.class)
public class Chat {
  private String user1;
  private String user2;
  private List<Map<String, String>> messages = new ArrayList<>();

  /**
   * Constructor for Chat class.
   *
   * @param string1 user1
   * @param string2 user2
   * @param messages that are sent between the two users
   */
  public Chat(String string1, String string2, List<Map<String, String>> messages) {
    this(string1, string2);
    this.messages = messages;
  }

  /**
   * Constructor for Chat Class.
   *
   * @param user1 who sends messages on the chat
   * @param user2 who sends messages in the chats back
   */
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
  
  /**
   * Maps the user to the messages they send to their match.
   *
   * @param user that chats with a match
   * @param message the user sends
   */
  public void sendMessage(String user, String message) {
    Map<String, String> messageMap = new HashMap<>();
    messageMap.put(user, message);
    messages.add(messageMap);
  }

}
