package use_case.startnewgame;

import entity.CurrentGame;

public class StartNewGameOutputData {

    private final CurrentGame currentGame;

    public StartNewGameOutputData(CurrentGame currentGame) {this.currentGame = currentGame;}

    public CurrentGame getCurrentGame() {return currentGame;}
}
