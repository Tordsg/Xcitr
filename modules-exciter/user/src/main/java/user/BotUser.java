package user;

import java.util.List;

/**
 * This class makes randomusers that are shown as matches.
 */

public class BotUser extends User {
  private boolean likeBack;
  private List<String> replies = List.of(
      "Heisann",
      "Hei",
      "Hva skjer?",
      "Hmm, er egentlig opptatt....",
      "Tar du med kikkert så vi kan se på damer? ;P",
      "Wow det å matche med deg gjorde dagen min!",
      "Gjorde det vondt når du falt ned fra himmelen",
      "Jeg har ost og vin, hva med fredag?",
      "Du, meg, rumba med gunn?",
      "Der intet er, er selv keiserens krav forgjeves",
      "Melk?",
      "Er du et jordskjelv? For du ristet verden min"
  );

  /**
   * Constructor for the BotUser class. 
   *
   * @param name string for the BotUsers name
   * @param age int for the BotUsers age
   * @param email string for the BotUsers email
   * @param likeback boolean value for whether they like the user back or not
   * @param imageId int id for the image the BotUser uses
   */

  public BotUser(String name, int age, String email, boolean likeback, int imageId) {
    this(name, age, email, likeback);
    super.setImageId(imageId);
  }

  /**
   * Constructor for the BotUser class.  
   *
   * @param name string for the BotUsers name
   * @param age int for the BotUsers age
   * @param email string for the BotUsers email
   * @param likeback boolean value for whether they like the user back or not
   */

  public BotUser(String name, int age, String email, boolean likeback) {
    super(name, age, email);
    this.likeBack = likeback;
  }

  /**
   * Constructor for BotUser class.
   *
   * @param name string for the BotUsers name
   * @param age int for the BotUsers age
   * @param userInformation string for the BotUsers bio
   * @param email string for the BotUsers email
   * @param likeback boolean value for whether they like the user back or not
   * @param imageId int for the image id that the bot user uses
   */

  public BotUser(String name, int age, String userInformation, 
      String email, boolean likeback, int imageId) {
    this(name, age, email, likeback, imageId);
    this.setUserInformation(userInformation);
  }

  @Override
  public void fireOnLike(String match) {
    if (likeBack) {
      super.fireOnLike(match);
    }
  }

  public boolean isLikeBack() {
    return likeBack;
  }

  public String reply() {
    return replies.get((int) (Math.random() * replies.size()));
  }

}
