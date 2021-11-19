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

  /**
   * Adds a user to the list of people who have an account on the app.
   *
   * @param user on the app
   */
  public void addUser(User user) {
    if (allUsers.contains(user)) {
      allUsers.remove(user);
    }
    allUsers.add(user);
  }

  /**
   * This method is there to override exisiting users.
   *
   * @param users
   *
   * @apiNote primarly used to add users from json file.
   */

  public void addUsers(List<User> users) {
    List<String> userMailList = allUsers.stream().map(User::getEmail).collect(Collectors.toList());
    for (User user : users) {
      if (userMailList.contains(user.getEmail())) {
        allUsers.remove(getUserByEmail(user.getEmail()));
      }
      userMailList.add(user.getEmail());
      allUsers.add(user);
    }
  }

  /**
   * Adds botUsers.
   */

  public void addSomePlaceholderUsers() {
    allUsers.add(new BotUser("John", 22, "Likes to take long hikes every sunday",
         "John@mail.no", true, 1));
    allUsers.add(new BotUser("Jane", 31, "Loves a good wine with some good company",
         "Jane@mail.no", true, 3));
    allUsers.add(new BotUser("Joe", 19, 
        "Superman is the best movie ever and I am searching for someone to share it with", 
        "Joe@mail.no", false, 4));
    allUsers.add(new BotUser("Derik", 27,
         "Looking for someone to snuggle with in front of the fireplace with a cup of hot chocolate.", 
         "Derik@mail.no", false, 5));
    allUsers.add(new BotUser("Diana", 23, 
        "I love to work out, wanna be my workout partner?", 
        "Diana@mail.no", false, 6));
    allUsers.add(new BotUser("Dani", 25, 
        "Favourite thing to do: travel!", "Dani@mail.no", true, 8));
    allUsers.add(new User("Roger", 25,
        "Swimming in the ocean is my all time favourite past time.", "Roger@mail.no"));
  }

  /**
   * Finds a random new user that is going to appear on the matching page.
   *
   * @return new user that is not on screen
   */

  public User getNextRandomUser(List<User> users) {
    List<User> tmp = allUsers.stream().filter(user
        -> !users.contains(user)).collect(Collectors.toList());
    return tmp.get((int) (Math.random() * tmp.size()));
  }

  public List<User> getTwoUniqueUsers(User user) {
    List<User> tmp = allUsers.stream().filter(u -> !u.equals(user)).collect(Collectors.toList());
    return pickNrandom(tmp, 2);
  }

  private static List<User> pickNrandom(List<User> lst, int n) {
    List<User> copy = new ArrayList<>(lst);
    Collections.shuffle(copy);
    return n > copy.size() ? copy.subList(0, copy.size()) : copy.subList(0, n);
  }

  /**
   * Gets specific users by emails.
   *
   * @param emails
   *
   * @return
   * 
   */
  public List<User> getUsersFromList(List<String> emails) {
    return allUsers.stream().filter(user
        -> emails.contains(user.getEmail())).collect(Collectors.toList());
  }

  public User getUserByEmail(String email) {
    return allUsers.stream().filter(u -> u.getEmail().equals(email)).findFirst().orElse(null);
  }

  public User getUserById(UUID id) {
    return allUsers.stream().filter(u
        -> u.getId() != null && u.getId().equals(id)).findFirst().orElse(null);
  }

  /**
   * Removes a user.
   *
   * @param user
   *
   */

  public void clearUser(User user) {
    allUsers.remove(allUsers.stream().filter(u
        -> u.getEmail().equals(user.getEmail())).findFirst().orElse(null));
  }

  /**
   * Finds how many times in a row user has liked another user.
   *
   * @param user
   *
   * @return number of likes in a row by current user
   */

  public int getLikeCount(User user, User userToCheckAgainst) {
    if (user.getLikedUsers()
        .containsKey(this.getUserByEmail(userToCheckAgainst.getEmail()).getEmail())) {
      return user.getLikedUsers().size();
    }
    return 0;
  }

  public List<User> getAllUsers() {
    return new ArrayList<>(allUsers);
  }

  public void setAllUsers(ArrayList<User> allUsers) {
    this.allUsers = allUsers;
  }

  /**
   * Core logic of exciter class. It discards one user and checks if the current
   * user has liked the other user.
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
