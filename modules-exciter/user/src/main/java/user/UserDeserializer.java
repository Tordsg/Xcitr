package user;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;

public class UserDeserializer extends StdDeserializer<User>{

    public UserDeserializer() { 
        this(null); 
    } 

    public UserDeserializer(Class<?> vc) { 
        super(vc); 
    }

    @Override
    public User deserialize(JsonParser jp, DeserializationContext ctxt) 
      throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);
        return deserialize(node); 
    }

    public User deserialize(JsonNode node) {
        if(node instanceof ObjectNode objectNode){
            User user = null;
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
              JsonNode userInfoNode = objectNode.get("userInformation");
              if (userInfoNode instanceof TextNode) {
                user.setUserInformation(userInfoNode.asText());
              }
              JsonNode passwordNode = objectNode.get("password");
              if (passwordNode instanceof TextNode) {
                user.setPassword(passwordNode.asText());
              }



              return user;
            }
            return null;
    }

}



