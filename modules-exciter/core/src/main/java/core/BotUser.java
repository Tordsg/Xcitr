package core;

public class BotUser extends User {
    private boolean likeBack;

    public BotUser(String name, int age, String email, boolean likeback) {
        super(name, age, email);
        this.likeBack = likeback;
    }

    @Override
    public void fireOnLike(User match) {
        if (likeBack) super.fireOnLike(match);
    }

}
