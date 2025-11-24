package use_case.ViewGameResult;
import entity.Card;
import entity.GameState;
import entity.CurrentGame;
import interface_adapter.ViewGameResult.ViewGameResultPresenter;
import interface_adapter.ViewGameResult.ViewGameResultViewModel;

import java.util.ArrayList;

public class ViewGameResultInteractor implements ViewGameResultInputBoundary {

    @Override
    public void execute(CurrentGame currentGame) {
        GameState gameState = currentGame.getGameState();
        if (gameState == GameState.ONGOING) {
            throw new IllegalStateException("Game is still ongoing. Result cannot be viewed.");
        }
        String gameResult = currentGame.outcome();
        ArrayList<Card> playerHand = (ArrayList<Card>) currentGame.getPlayerHand();
        ArrayList<Card> dealerHand = (ArrayList<Card>) currentGame.getDealerHand();

        int playerScore = calculateScore(playerHand);
        int dealerScore = calculateScore(dealerHand);

        ViewGameResultViewModel viewGameResultViewModel = new ViewGameResultViewModel();
        ViewGameResultPresenter presenter = new ViewGameResultPresenter(viewGameResultViewModel);

        ViewGameResultOutputData outputData = new ViewGameResultOutputData(currentGame, gameResult, playerScore, dealerScore);
        presenter.presentGameResult(outputData);
    }

    private int calculateScore(ArrayList<Card> hand) {
        int score = 0;
        int numAces = 0;
        for (Card card : hand) {
            String value = card.getValue();
            if (value.equals("J") || value.equals("Q") || value.equals("K")) {
                score += 10;
            } else if (value.equals("A")) {
                score += 11;
                numAces++;
            } else {
                score += Integer.parseInt(value);
            }
        }

        while (score > 21 && numAces > 0) {
            score -= 10;
            numAces--;
        }
        return score;
    }
}
