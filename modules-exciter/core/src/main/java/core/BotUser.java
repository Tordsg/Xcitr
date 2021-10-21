package core;

/**
 * This class makes randomusers that are shown as matches
 */

public class BotUser extends User {
  private boolean likeBack;

  public BotUser(String name, int age, String email, boolean likeback) {
    super(name, age, email);
    this.likeBack = likeback;
  }

  public BotUser(String name, int age, String userInformation, String email, boolean likeback) {
    super(name, age, userInformation, email);
    this.likeBack = likeback;
  }

  @Override
  public void fireOnLike(User match) {
    if (likeBack){
      super.fireOnLike(match);
    }
  }
  public boolean isLikeBack() {
    return likeBack;
  }

}
