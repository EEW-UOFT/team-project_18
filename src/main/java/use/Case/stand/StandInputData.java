package use.Case.stand;

import entity.CurrentGame;
import entity.User;

public class StandInputData {

    private final CurrentGame currentGame;
    private final User user;

    public StandInputData(CurrentGame currentGame, User user) {
        this.currentGame = currentGame;
        this.user = user;
    }

    public CurrentGame getCurrentGame() { return currentGame; }
    public User getUser() { return user; }
}
