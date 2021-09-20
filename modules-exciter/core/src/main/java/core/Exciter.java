package core;

import java.util.ArrayList;

import json.FileHandler;

public class Exciter {

   private FileHandler fileHandler;

   // TODO: add backend logic and connect to controller
   private ArrayList<User> allUsers = new ArrayList<>();
   private User onScreenUser;

   public void addSomePlaceholderUsers() {
      allUsers.add(new User("John", 22));
      allUsers.add(new User("Jane", 31));
      allUsers.add(new User("Joe", 19));
   }

   // Current user placeholder before logging in is implemented
   private User currentUser = new User("Ulf Reidar", 25, "Camping, guitar, professional speed knitter");

   public User getNextUser() {
      for(User user:allUsers){
         if(!currentUser.getAlreadyMatched().containsKey(user)){
            setOnScreenUser(user);
            return user;
         }
      }
      //TODO: handle no more users
      return null;
   }

   public void setOnScreenUser(User user) {
      onScreenUser = user;
   }

   public User getOnScreenUser() {
      return onScreenUser;
   }

   public boolean pressedLike(){
      currentUser.fireOnLike(onScreenUser);
      return currentUser.checkIfMatch(onScreenUser);
   }

   public void saveUserData() {
      fileHandler.saveUser(currentUser.getName(), currentUser.getAge(), currentUser.getUserInformation().toCharArray());
   }

}