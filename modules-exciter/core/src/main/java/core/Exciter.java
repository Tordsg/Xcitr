package core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;


public class Exciter {
    
   // TODO: connect to controller
   private ArrayList<User> allUsers = new ArrayList<>();
   private User onScreenUser1;
   private User onScreenUser2;
 
   

   public Exciter(){
      addSomePlaceholderUsers();
      ArrayList<User> onscreenUsers = getNextUsers();
      setOnScreenUser(onscreenUsers.get(0), onscreenUsers.get(1));
      


   }

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


   public User getCurrentUser() {
      return currentUser;
   }

   public void setCurrentUser(User user) {
      currentUser = user;
   }

   public ArrayList<User> getNextUsers() {
      int[] randomUsers = new Random().ints(0, allUsers.size() - 1).distinct().limit(2).toArray();
      setOnScreenUser(allUsers.get(randomUsers[0]), allUsers.get(randomUsers[1]));
      return new ArrayList<>(Arrays.asList(allUsers.get(randomUsers[0]),allUsers.get(randomUsers[1])));
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
  
}