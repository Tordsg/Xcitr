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
      allUsers.add(new BotUser("John", 22, "John@mail"));
      allUsers.add(new BotUser("Jane", 31, "Jane@mail"));
      allUsers.add(new BotUser("Joe", 19, "Joe@mail"));
      allUsers.add(new BotUser("Derik", 27, "Derik@mail"));
      allUsers.add(new BotUser("Diana", 23, "Diana@mail"));
      allUsers.add(new BotUser("Dani", 25, "Dani@mail"));
   }

   // Current user placeholder before logging in is implemented
   private User currentUser = new User("Ulf Reidar", 25, "Camping, guitar, professional speed knitter", "Ulf@mail");


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
      onScreenUser1.fireOnLike(currentUser);
      return onScreenUser1.checkIfMatch(currentUser);
   }

   public boolean pressedLikeSecond(){
      onScreenUser2.fireOnLike(currentUser);
      return onScreenUser2.checkIfMatch(currentUser);
   }

}