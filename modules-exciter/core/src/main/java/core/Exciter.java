package core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/**
 * The main logic class.
 */

public class Exciter {
  
  private List<User> allUsers = new ArrayList<>();
  private User onScreenUser1;
  private User onScreenUser2;

  // Current user placeholder before logging in is implemented.
  private User currentUser = new User("admin", 18, "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut", "admin");

  /**
   * Constructor in the class.
   */

  public Exciter() {
    addSomePlaceholderUsers();
    getNextUsers();
    currentUser.setPassword("admin");
  }

  /**
   * Will check that users to be added does not exist in the list.
   * @param users to be added to the list of all users.
   * 
   * @apiNote primarly used to add users from JSON file.
   */

  public void addUsers(List<User> users) {
    List<String> userMailList = allUsers.stream().map(User::getEmail).collect(Collectors.toList());
    for (User user : users) {
      if (!userMailList.contains(user.getEmail())) {
        allUsers.add(user);
      }
    }
  }

  /**
   * adds botUsers 
   */

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

  /**
   * Method will also make sure that user is not among allUsers,
   * to make sure that user can't like himself.
   * @param user to be set as current user
   * 
   */
   
  public void setCurrentUser(User user) {
    User localUser = allUsers.stream().filter(u -> u.getEmail().equals(user.getEmail())).findFirst().orElse(null);
    allUsers.remove(localUser);
    currentUser = user;
  }

  /**
   *
   * @return two new users to be displayed on the screen
   * @apiNote this method does not check against onScreenUser1 and onScreenUser2
   */

  public ArrayList<User> getNextUsers() {
    int[] randomUsers = ThreadLocalRandom.current().ints(0, allUsers.size() - 1).distinct().limit(2).toArray();
    setOnScreenUser(allUsers.get(randomUsers[0]), allUsers.get(randomUsers[1]));
    return new ArrayList<>(Arrays.asList(allUsers.get(randomUsers[0]), allUsers.get(randomUsers[1])));
  }

  /**
   *
   * @return two new unique users that are not on screen
   */

  public ArrayList<User> refreshUsers() {
    ArrayList<User> tempUserList = allUsers.stream().filter(a -> a != onScreenUser1 && a != onScreenUser2)
            .collect(Collectors.toCollection(ArrayList::new));

    int[] randomUsers = ThreadLocalRandom.current().ints(0, tempUserList.size() - 1).distinct().limit(2).toArray();
    setOnScreenUser(tempUserList.get(randomUsers[0]), tempUserList.get(randomUsers[1]));

    return new ArrayList<>(Arrays.asList(tempUserList.get(randomUsers[0]), tempUserList.get(randomUsers[1])));
  }

  /**
   * @return new user that is not on screen
   */
  public User getNextRandomUser() {
    ArrayList<User> tempUserList = allUsers.stream().filter(a -> a != onScreenUser1 && a != onScreenUser2)
            .collect(Collectors.toCollection(ArrayList::new));

    int randomUser = (int) (Math.random() * tempUserList.size()) + 0;

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
   * @param user
   * @return number of likes in a row by current user
   * 
   */

  public int getOnScreenUserLikeCount(User user) {
    if (currentUser.getLikedUsers().containsKey(user)) {
      return currentUser.getLikedUsers().get(user);
    }
    return 0;
  }

  public List<User> getAllUsers() {
    return new ArrayList<>(allUsers);
  }
  
  public void removeFromAllUsers(User user) {
    allUsers.remove(user);
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
   * Core logic of exciter class.
   * It discards one user and checks if the current user has liked the other user
   *
   * @return true if the user liked the other user three times in a row
  */

  public boolean discardSecond() {
    if (currentUser.haveLikedUser(onScreenUser2)) {
      currentUser.resetUserMatch(onScreenUser2);
    }
    currentUser.fireOnLike(onScreenUser1);
    currentUser.resetUserMatch(onScreenUser2);
  
    if (onScreenUser1 instanceof BotUser) {
      onScreenUser1.fireOnLike(currentUser);
    }
    onScreenUser2 = getNextRandomUser();
    boolean match = currentUser.haveLikedUser(onScreenUser1);
    currentUser.checkIfMatch(onScreenUser1);
    if (currentUser.haveLikedUser(onScreenUser1)) {
      onScreenUser1 = getNextRandomUser();
    }
    return match;
  }

  /**
   * Core logic of exciter class.
   * It discards one user and checks if the current user has liked the other user
   *
   * @return true if the user liked the other user three times in a row
  */

  public boolean discardFirst() {
    if (currentUser.haveLikedUser(onScreenUser2)) {
      currentUser.resetUserMatch(onScreenUser2);
    }
    currentUser.fireOnLike(onScreenUser2);
    currentUser.resetUserMatch(onScreenUser1);
    if (onScreenUser2 instanceof BotUser) {
      onScreenUser2.fireOnLike(currentUser);
    }
    onScreenUser1 = getNextRandomUser();
    boolean match = currentUser.haveLikedUser(onScreenUser2);
    currentUser.checkIfMatch(onScreenUser2);
    if (currentUser.haveLikedUser(onScreenUser2)) {
      onScreenUser2 = getNextRandomUser();
    }

    return match;
  }

  public List<String> getCurrentUserMatches() {
    return currentUser.getMatches();
  }

}
