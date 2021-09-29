package core;

public class BotUser extends User {
    public BotUser(String name, int age, String email) {
        super(name, age,email);
    }


    @Override
    public void fireOnLike(User match) {
        match.fireOnLike(this);
        super.addUserOnMatch(match);
    }

}
