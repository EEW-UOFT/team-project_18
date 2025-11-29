package interfaceadapter.gamerule;

import use.Case.gamerule.GameRuleOutputBoundary;

public class GameRulePresenter implements GameRuleOutputBoundary {
    private final GameRuleViewModel gameRuleViewModel;
    public GameRulePresenter(GameRuleViewModel gameRuleViewModel) {
        this.gameRuleViewModel = gameRuleViewModel;
    }
    public void presentGameRules(String rules) {
        this.gameRuleViewModel.setGameRules(rules);
    }
}
