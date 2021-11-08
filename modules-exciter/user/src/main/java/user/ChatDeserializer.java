package user;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;

public class ChatDeserializer extends StdDeserializer<Chat> {
    private ObjectMapper mapper = new ObjectMapper();

    protected ChatDeserializer(JavaType valueType) {
        super(valueType);
    }

    protected ChatDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Chat deserialize(JsonParser jp, DeserializationContext arg1) throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);
        return deserialize(node);
    }

    public Chat deserialize(JsonNode node) {
        String user1mail = null;
        String user2mail = null;
        List<HashMap<String,String>> messages;
        if (node == null) {
            return null;
        }
        if (node instanceof ObjectNode objectNode){
            JsonNode user1 = objectNode.get("user1");
            if(user1 instanceof TextNode){
                user1mail = user1.asText();
            }
            JsonNode user2 = objectNode.get("user2");
            if(user2 instanceof TextNode){
                user2mail = user2.asText();
            }
            JsonNode messagesNode = objectNode.get("messages");
            messages = mapper.convertValue(messagesNode, new TypeReference<List<HashMap<String, String>>>(){});

            if(user1mail != null && user2mail != null && messages != null){
                return new Chat(user1mail, user2mail, messages);
            }
        }
        return null;
    }

}
