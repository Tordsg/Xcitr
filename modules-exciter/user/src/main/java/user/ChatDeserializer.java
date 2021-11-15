package user;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Class for Chat deserializing.
 */
public class ChatDeserializer extends StdDeserializer<Chat> {
  private ObjectMapper mapper = new ObjectMapper();

  protected ChatDeserializer() {
    this(null);
  }

  protected ChatDeserializer(Class<?> vc) {
    super(vc);
  }

  @Override
  public Chat deserialize(JsonParser jp, DeserializationContext arg1) throws IOException, JsonProcessingException {
    JsonNode node = jp.getCodec().readTree(jp);
    return deserialize(node);
  }

  /**
   * Method for deserializing a Chat object.
   *
   * @param node JsonNode that can be deserialized to an ObjectNode
   *
   * @return chat, otherwise null
   */
  public Chat deserialize(JsonNode node) {
    Chat chat = new Chat();
    List<Map<String, String>> messages;
    if (node == null) {
      return null;
    }
    if (node instanceof ObjectNode objectNode) {
      JsonNode user1 = objectNode.get("user1");
      if (user1 instanceof TextNode) {
        chat.setUser1(user1.asText());
      }
      JsonNode user2 = objectNode.get("user2");
      if (user2 instanceof TextNode) {
        chat.setUser2(user2.asText());
      }
      JsonNode messagesNode = objectNode.get("messages");
      messages = mapper.convertValue(messagesNode, new TypeReference<List<Map<String, String>>>() {
      });
      chat.setMessages(messages);
      return chat;
    }
    return null;
  }

}
