package core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

public class Exciter {

   private ArrayList<User> allUsers = new ArrayList<>();
   private User onScreenUser1;
   private User onScreenUser2;

   // Current user placeholder before logging in is implemented
   private User currentUser = new User("Ulf Reidar", 25, "Camping, guitar, professional speed knitter", "Ulf@mail");

   public Exciter() {
      addSomePlaceholderUsers();
      getNextUsers();
   }

   public void addSomePlaceholderUsers() {
      allUsers.add(new BotUser("John", 22, "John@mail",true));
      allUsers.add(new BotUser("Jane", 31, "Jane@mail",true));
      allUsers.add(new BotUser("Joe", 19, "Joe@mail",false));
      allUsers.add(new BotUser("Derik", 27, "Derik@mail",false));
      allUsers.add(new BotUser("Diana", 23, "Diana@mail",false));
      allUsers.add(new BotUser("Dani", 25, "Dani@mail",true));
      allUsers.add(new User("Roger", 25, "Roger@mail"));
   }

   public User getCurrentUser() {
      return currentUser;
   }

   public void setCurrentUser(User user) {
      currentUser = user;
   }

   /**
    * 
    * @return a list of the next users in the file of users
    */
   public ArrayList<User> getNextUsers() {
      int[] randomUsers = new Random().ints(0, allUsers.size() - 1).distinct().limit(2).toArray();

      setOnScreenUser(allUsers.get(randomUsers[0]), allUsers.get(randomUsers[1]));

      return new ArrayList<>(Arrays.asList(allUsers.get(randomUsers[0]), allUsers.get(randomUsers[1])));
   }

   /**
    * 
    * @return two users who are not curretly portrayed on the app
    */
   public ArrayList<User> refreshUsers(){
      ArrayList<User> tempUserList = allUsers.stream().filter(a -> a != onScreenUser1 && a != onScreenUser2)
            .collect(Collectors.toCollection(ArrayList::new));

      int[] randomUsers = new Random().ints(0, tempUserList.size() - 1).distinct().limit(2).toArray();
      setOnScreenUser(tempUserList.get(randomUsers[0]), tempUserList.get(randomUsers[1]));

      return new ArrayList<>(Arrays.asList(tempUserList.get(randomUsers[0]), tempUserList.get(randomUsers[1])));
   }

   /**
    * 
    * @return a random user from the file archive
    */
   public User getNextRandomUser() {
      ArrayList<User> tempUserList = allUsers.stream().filter(a -> a != onScreenUser1 && a != onScreenUser2)
            .collect(Collectors.toCollection(ArrayList::new));

      int randomUser = new Random().nextInt(tempUserList.size());

      return tempUserList.get(randomUser);
   }


   public void setOnScreenUser1(User user) {
      onScreenUser1 = user;
   }

   public void setOnScreenUser2(User user) {
      onScreenUser2 = user;
   }

   public User getOnScreenUser1() {
      return onScreenUser1;
   }

   public User getOnScreenUser2() {
      return onScreenUser2;
   }

   /**
    * 
    * @param user
    * @return how many likes an onscreen user has
    */
   public int getOnScreenUserLikeCount(User user) {
      if(currentUser.getAlreadyMatched().containsKey(user.getEmail())) {
         return currentUser.getAlreadyMatched().get(user.getEmail());
      }
      return 0;
   }

   public ArrayList<User> getAllUsers() {
      return allUsers;
   }

   public void setAllUsers(ArrayList<User> allUsers) {
      this.allUsers = allUsers;
   }

   public void setOnScreenUser(User user, User user2) {
      onScreenUser1 = user;
      onScreenUser2 = user2;
   }

   public ArrayList<User> getOnScreenUsers() {
      return new ArrayList<>(Arrays.asList(onScreenUser1, onScreenUser2));
   }

   /**
    * 
    * @return
    */
   public boolean pressedLikeFirst() {
      currentUser.fireOnLike(onScreenUser1);
      currentUser.resetUserMatch(onScreenUser2);
      if (onScreenUser1 instanceof BotUser) {
         onScreenUser1.fireOnLike(currentUser);
      }
      onScreenUser2 = getNextRandomUser();
      return onScreenUser1.checkIfMatch(currentUser);
   }

   public boolean pressedLikeSecond() {
      currentUser.fireOnLike(onScreenUser2);
      currentUser.resetUserMatch(onScreenUser1);
      if (onScreenUser2 instanceof BotUser) {
         onScreenUser2.fireOnLike(currentUser);
      }
      onScreenUser1 = getNextRandomUser();
      return onScreenUser2.checkIfMatch(currentUser);
   }

}