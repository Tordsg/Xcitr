package core;

import java.util.ArrayList;
import java.util.Arrays;

import json.FileHandler;

public class Exciter {

   private FileHandler fileHandler;

   // TODO: add backend logic and connect to controller
   private ArrayList<User> allUsers = new ArrayList<>();
   private User onScreenUser1;
   private User onScreenUser2;

   public void addSomePlaceholderUsers() {
      allUsers.add(new User("John", 22));
      allUsers.add(new User("Jane", 31));
      allUsers.add(new User("Joe", 19));
      allUsers.add(new User("Derik", 27));
      allUsers.add(new User("Diana", 23));
      allUsers.add(new User("Dani", 25));
   }

   // Current user placeholder before logging in is implemented
   private User currentUser = new User("Ulf Reidar", 25, "Camping, guitar, professional speed knitter");

   public User getNextUsers() {
      for(User user:allUsers){
         if(!currentUser.getAlreadyMatched().containsKey(user)){
            //TODO: create algorithm to select two random people
            setOnScreenUser(user,user);
            return user;
         }
      }
      //TODO: handle no more users
      return null;
   }

   public void setOnScreenUser(User user, User user2) {
      onScreenUser1 = user;
      onScreenUser2 = user2;
   }

   public ArrayList<User> getOnScreenUsers() {
      return new ArrayList<>(Arrays.asList(onScreenUser1,onScreenUser2));
   }

   public boolean pressedLikeFirst(){
      currentUser.fireOnLike(onScreenUser1);
      return currentUser.checkIfMatch(onScreenUser1);
   }

   public boolean pressedLikeSecond(){
      currentUser.fireOnLike(onScreenUser2);
      return currentUser.checkIfMatch(onScreenUser2);
   }

   public void saveUserData() {
      fileHandler.saveUser(currentUser.getName(), currentUser.getAge(), currentUser.getUserInformation().toCharArray());
   }

}