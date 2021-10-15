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
   private User currentUser = new User("admin", 18, "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut", "admin");

   public Exciter() {
      addSomePlaceholderUsers();
      getNextUsers();
      currentUser.setPassword("admin");
   }

   public void addSomePlaceholderUsers() {
      allUsers.add(new BotUser("John", 22, "John@mail", true));
      allUsers.add(new BotUser("Jane", 31, "Jane@mail", true));
      allUsers.add(new BotUser("Joe", 19, "Joe@mail", false));
      allUsers.add(new BotUser("Derik", 27, "Derik@mail", false));
      allUsers.add(new BotUser("Diana", 23, "Diana@mail", false));
      allUsers.add(new BotUser("Dani", 25, "Dani@mail", true));
      allUsers.add(new User("Roger", 25, "Roger@mail"));
   }

   public User getCurrentUser() {
      return currentUser;
   }

   public void setCurrentUser(User user) {
      currentUser = user;
   }

   public ArrayList<User> getNextUsers() {
      int[] randomUsers = new Random().ints(0, allUsers.size() - 1).distinct().limit(2).toArray();

      setOnScreenUser(allUsers.get(randomUsers[0]), allUsers.get(randomUsers[1]));

      return new ArrayList<>(Arrays.asList(allUsers.get(randomUsers[0]), allUsers.get(randomUsers[1])));
   }

   public ArrayList<User> refreshUsers() {
      ArrayList<User> tempUserList = allUsers.stream().filter(a -> a != onScreenUser1 && a != onScreenUser2)
            .collect(Collectors.toCollection(ArrayList::new));

      int[] randomUsers = new Random().ints(0, tempUserList.size() - 1).distinct().limit(2).toArray();
      setOnScreenUser(tempUserList.get(randomUsers[0]), tempUserList.get(randomUsers[1]));

      return new ArrayList<>(Arrays.asList(tempUserList.get(randomUsers[0]), tempUserList.get(randomUsers[1])));
   }

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

   public int getOnScreenUserLikeCount(User user) {
      if (currentUser.getLikedUsers().containsKey(user.getEmail())) {
         return currentUser.getLikedUsers().get(user.getEmail());
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

   public ArrayList<User> getCurrentUserMatches() {
      return allUsers.stream().filter(a -> currentUser.checkIfMatch(a))
            .collect(Collectors.toCollection(ArrayList::new));
   }

}