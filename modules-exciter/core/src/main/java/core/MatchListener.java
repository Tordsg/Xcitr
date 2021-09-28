package core;

public interface MatchListener {

    public void fireOnLike(User match);
    public boolean checkIfMatch(User user);
}
