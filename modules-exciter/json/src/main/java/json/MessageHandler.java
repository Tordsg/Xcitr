package json;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import user.Chat;

public class MessageHandler {

    public MessageHandler() {
        createFile();
    }

    private JSONParser parser = new JSONParser();
    String path = "../json/src/main/resources/messages.json";

    public void createFile() {
        try {
            File file = new File(path);
            if (file.createNewFile()) {
                System.out.println("File created");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @SuppressWarnings("unchecked")
    public void saveChat(Chat chat) {
        JSONArray userArray = new JSONArray();
        List<Chat> chats = this.getChats() == null ? new ArrayList<>() : this.getChats();
        if(chat != null){
            removeFromChat(chat.getUser1(), chat.getUser2(), chats);
        }
        chats.add(chat);
        for (Chat c : chats) {
            JSONObject chatData = new JSONObject();
            chatData.put("user1", c.getUser1());
            chatData.put("user2", c.getUser2());
            chatData.put("message", c.getMessages());
            userArray.add(chatData);
        }
        try {
            // OutputStreamWriter is used to force UTF-8 encoding since fileWriter is using
            // wrong encoding on older mac models
            BufferedWriter fileWriter = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream(path), StandardCharsets.UTF_8));
            fileWriter.write(userArray.toJSONString());
            fileWriter.close();
        } catch (FileNotFoundException e) {
            createFile();
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public List<Chat> getChats() {
        try (BufferedReader fileReader = new BufferedReader(
                new InputStreamReader(new FileInputStream(path), "UTF-8"))) {
            List<Chat> chat = new ArrayList<>();
            JSONArray chatArray = (JSONArray) parser.parse(fileReader);
            if (chatArray.isEmpty()) {
                return null;
            }
            for (Object chats : chatArray) {
                JSONObject chatData = (JSONObject) chats;
                String user1 = String.valueOf(chatData.get("user1"));
                String user2 = String.valueOf(chatData.get("user2"));
                List<HashMap<String, String>> messages = getMessages(chatData);
                Chat c = new Chat(user1, user2, messages);
                chat.add(c);
            }
            return chat;

        } catch (FileNotFoundException e) {
            createFile();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            return null;
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private List<HashMap<String, String>> getMessages(JSONObject chat) {
        JSONArray messages = (JSONArray) chat.get("message");
        List<HashMap<String, String>> messageList = new ArrayList<>();
        messages.forEach(m -> {
            HashMap<String,String> message = (HashMap<String,String>) m;
            messageList.add(message);
        });
        return messageList;
    }

    private void removeFromChat(String user1, String user2, List<Chat> chats) {
        if(chats == null || chats.isEmpty()) {
            return;
        }
        for (Chat chat : chats) {
            if(chat.getUser1().equals(user1) && chat.getUser2().equals(user2)){
                chats.remove(chat);
                return;
            }
        }
    }

    public Chat getChat(String user1, String user2) {
        List<Chat> chats = this.getChats();
        if(chats == null || chats.isEmpty()) {
            return null;
        }
        for (Chat chat : chats) {
            if(chat.getUser1().equals(user1) && chat.getUser2().equals(user2)){
                return chat;
            }
        }
        return null;
    }

}
