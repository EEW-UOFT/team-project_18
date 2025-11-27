package use.Case.hit;

import entity.CurrentGame;

public class HitOutputData {

    private final CurrentGame currentGame;

    public HitOutputData(CurrentGame currentGame) {
        this.currentGame = currentGame;
    }

    public CurrentGame getCurrentGame() {
        return currentGame;
    }
}
