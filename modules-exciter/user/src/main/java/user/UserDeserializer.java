package user;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;

public class UserDeserializer extends StdDeserializer<User> {

  private ObjectMapper mapper = new ObjectMapper();

  public UserDeserializer() {
    this(null);
  }

  public UserDeserializer(Class<?> vc) {
    super(vc);
  }

  @Override
  public User deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
    JsonNode node = jp.getCodec().readTree(jp);
    return deserialize(node);
  }

  public User deserialize(JsonNode node) {
    if (node == null) {
      return null;
    }
    if (node instanceof ObjectNode objectNode) {
      User user = new User();
      JsonNode nameNode = objectNode.get("name");
      if (nameNode instanceof TextNode) {
        user.setName(nameNode.asText());
      }
      JsonNode ageNode = objectNode.get("age");
      if (ageNode instanceof IntNode) {
        user.setAge(ageNode.asInt());
      }
      JsonNode emailNode = objectNode.get("email");
      if (emailNode instanceof TextNode) {
        user.setEmail(emailNode.asText());
      }
      ArrayNode matchNode = (ArrayNode) objectNode.get("matches");

      for (JsonNode string : matchNode) {
        if (string instanceof TextNode) {
          user.addMatch(string.asText());
        }

      }
      JsonNode userInfoNode = objectNode.get("userInformation");
      if (userInfoNode instanceof TextNode) {
        user.setUserInformation(userInfoNode.asText());
      }
      JsonNode passwordNode = objectNode.get("password");
      if (passwordNode instanceof TextNode) {
        user.setPassword(passwordNode.asText());
      }
      JsonNode userIdNode = objectNode.get("id");
      if (userIdNode instanceof TextNode) {
        user.setId(UUID.fromString(userIdNode.asText()));
      }
      JsonNode likedUserNode = objectNode.get("likedUsers");
      HashMap<String, Integer> result = mapper.convertValue(likedUserNode,
          new TypeReference<HashMap<String, Integer>>() {
          });
      if (result != null) {
        user.setLikedUsers(result);
      }
      JsonNode imageId = objectNode.get("imageId");
      if (imageId instanceof IntNode) {
        user.setImageId(imageId.asInt());
      }

      return user;
    }
    return null;
  }

}
