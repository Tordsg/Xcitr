package core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import user.BotUser;
import user.User;

/**
 * The main logic class.
 */

public class Exciter {

  private List<User> allUsers = new ArrayList<>();

  /**
   * Constructor for the class.
   */

  public Exciter() {
    addSomePlaceholderUsers();
  }

  public void addUser(User user) {
    if (!allUsers.contains(user)) {
      allUsers.add(user);
    }
    throw new IllegalArgumentException("User already exists.");
  }

  /**
   * Will check that users to be added does not exist in the list.
   *
   * @param users to be added to the list of all users.
   *
   * @apiNote primarly used to for testing.
   */

  public void addUsers(List<User> users) {
    List<String> userMailList = allUsers.stream().map(User::getEmail).collect(Collectors.toList());
    for (User user : users) {
      if (!userMailList.contains(user.getEmail())) {
        allUsers.add(user);
        userMailList.add(user.getEmail());
      }
    }
  }
  /**
   * This method is there to override exisiting users.
   * @param users
   *
   * @apiNote primarly used to add users from json file.
   */
  public void addUsersFromFile(List<User> users) {
    List<String> userMailList = allUsers.stream().map(User::getEmail).collect(Collectors.toList());
    for (User user : users) {
      if (userMailList.contains(user.getEmail())) {
        allUsers.remove(getUserByEmail(user.getEmail()));
      }
      allUsers.add(user);
    }
  }

  /**
   * adds botUsers
   */

  public void addSomePlaceholderUsers() {
    allUsers.add(new BotUser("John", 22, "John@mail.no", true, 1));
    allUsers.add(new BotUser("Jane", 31, "Jane@mail.no", true, 3));
    allUsers.add(new BotUser("Joe", 19, "Joe@mail.no", false, 4));
    allUsers.add(new BotUser("Derik", 27, "Derik@mail.no", false, 5));
    allUsers.add(new BotUser("Diana", 23, "Diana@mail.no", false, 6));
    allUsers.add(new BotUser("Dani", 25, "Dani@mail.no", true, 8));
    allUsers.add(new User("Roger", 25, "Roger@mail.no"));
  }

  /**
   * @return new user that is not on screen
   */
  
  public User getNextRandomUser(List<User> users) {
    List<User> tmp = allUsers.stream().filter(user -> !users.contains(user)).collect(Collectors.toList());
    return tmp.get((int) (Math.random() * tmp.size()));
  }

  public List<User> getTwoUniqueUsers(User user) {
    List<User> tmp = allUsers.stream().filter(u -> !u.equals(user)).collect(Collectors.toList());
    return pickNRandom(tmp, 2);
  }

  private static List<User> pickNRandom(List<User> lst, int n) {
    List<User> copy = new ArrayList<>(lst);
    Collections.shuffle(copy);
    return n > copy.size() ? copy.subList(0, copy.size()) : copy.subList(0, n);
  }

  public List<User> getUsersFromList(List<String> emails) {
    return allUsers.stream().filter(user -> emails.contains(user.getEmail())).collect(Collectors.toList());
  }

  public User getUserByEmail(String email) {
    return allUsers.stream().filter(u -> u.getEmail().equals(email)).findFirst().orElse(null);
  }

  public User getUserById(UUID id) {
    return allUsers.stream().filter(u -> u.getId() != null && u.getId().equals(id)).findFirst().orElse(null);
  }

  public void clearUser(User user) {
    allUsers.remove(allUsers.stream().filter(u -> u.getEmail().equals(user.getEmail())).findFirst().orElse(null));
  }

  /**
   * @param user
   * 
   * @return number of likes in a row by current user
   *
   */

  public int getLikeCount(User user, User userToCheckAgainst) {
    if (user.getLikedUsers().containsKey(this.getUserByEmail(userToCheckAgainst.getEmail()).getEmail())) {
      return user.getLikedUsers().size();
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

  /**
   * Core logic of exciter class. It discards one user and checks if the current
   * user has liked the other user
   *
   * @return true if the user liked the other user three times in a row
   */

  public boolean likePerson(User userWhoLikes, User userWhoIsLiked) {
    userWhoLikes.fireOnLike(userWhoIsLiked.getEmail());
    if (userWhoIsLiked instanceof BotUser) {
      userWhoIsLiked.fireOnLike(userWhoLikes.getEmail());
    }
    if (userWhoLikes.getLikedUsers().containsKey(userWhoIsLiked.getEmail())
        && userWhoLikes.getLikedUsers().get(userWhoIsLiked.getEmail()) > 3) {
      userWhoLikes.resetUserMatchToOne(userWhoIsLiked.getEmail());
    }
    return userWhoLikes.checkIfMatch(userWhoIsLiked);
  }

  public void resetLikes(User user, User userToReset) {
    user.resetUserMatch(userToReset.getEmail());
  }

  public List<String> getUserMatches(User user) {
    return user.getMatches();
  }

}
