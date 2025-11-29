package interfaceadapter.gamerule;

import use.Case.gamerule.GameRuleInteractor;

public class GameRuleController {
    private final GameRuleInteractor gameRuleInteractor;

    public GameRuleController(GameRuleInteractor gameRuleInteractor) {
        this.gameRuleInteractor = gameRuleInteractor;
    }

    public void execute() {
        gameRuleInteractor.execute();
    }
}
