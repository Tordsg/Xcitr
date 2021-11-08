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
    "Du, meg, rumba med gunn?"
  );

  public BotUser(String name, int age, String email, boolean likeback) {
    super(name, age, email);
    this.likeBack = likeback;
  }

  public BotUser(String name, int age, String userInformation, String email, boolean likeback) {
    super(name, age, userInformation, email);
    this.likeBack = likeback;
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
