package user;

import java.util.List;
/**
 * This class makes randomusers that are shown as matches.
 */
public class BotUser extends User {
  private boolean likeBack;
  private List<String> replies = List.of(
      "Hello",
      "Heyyy cutie",
      "Whats up?",
      "Hmm, I´m kinda busy rn....",
      "Can you bring some binoculars so we can look at people? ;P",
      "Wow matching with you made my day!",
      "Did it hurt when you fell from heaven?",
      "I have red wine and cheese, are free friday?",
      "Wanna go dancing?",
      "What a lovely day to be alive",
      "Paint and sip?",
      "Woah is that an earthquake? or did you just rock my world?",
      "Well here I am, what are your other two wishes?",
      "I must be a snowflake because I´ve fallen for you!",
      "Are you sure?",
      "Do you wanna watch a christmas movie?",
      "Chocolate!!!",
      "Life without you is like a broken pencil... pointless",
      "What about a picnic under the stars?",
      "Hey my name is Microsoft. Can I crash at your place tonight?",
      "Do you like raisins? How do you feel about a date?",
      "Did your license get suspended for driving all these guys crazy?",
      "I was feeling a little off today—but you’ve turned me on again!",
      "Are you a loan? Because you sure have my interest!",
      "Idk",
      "Sure",
      "<3",
      "They say nothing lasts forever—so would you be my nothing?",
      "Are you a charger? Because I’m dying without you!",
      "What about you?",
      "Boring://"
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
   * @param name string for the botUsers name
   * @param age int for the botUsers age
   * @param userInformation string for the BotUsers bio
   * @param email string for the botUsers email
   * @param likeback boolean value for whether they like the user back or not
   * @param imageId int for the image id that the botUser uses
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
