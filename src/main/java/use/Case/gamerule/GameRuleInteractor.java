package use.Case.gamerule;

import entity.GameState;
import interfaceadapter.gamerule.GameRulePresenter;

public class GameRuleInteractor implements GameRuleInputBoundary {
    private final GameRulePresenter presenter;
    public GameRuleInteractor(GameRulePresenter presenter) {
        this.presenter = presenter;
    }
    public void execute() {
        String gameRules = "Rules of Blackjack:\n" +
                "1. The goal is to have a hand value as close to 21 as possible without exceeding it.\n" +
                "2. Number cards are worth their face value, face cards are worth 10, and Aces can be worth 1 or 11.\n" +
                "3. Players are dealt two cards and can choose to 'hit' (take another card) or 'stand' (end their turn).\n" +
                "4. If a player's hand exceeds 21, they 'bust' and lose the game.\n" +
                "5. The dealer must hit until their hand is at least 17.\n" +
                "6. If the player has a higher hand value than the dealer without busting, the player wins.\n" +
                "7. If the dealer busts, all remaining players win.\n" +
                "8. A 'Blackjack' (an Ace and a 10-value card) beats any other 21-value hand.";
        presenter.presentGameRules(gameRules);
    }
}
