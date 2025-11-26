package use.Case.viewgameresult;

import java.util.ArrayList;

import entity.Card;
import entity.CurrentGame;
import entity.GameState;
import interfaceadapter.ViewGameResult.ViewGameResultPresenter;
import interfaceadapter.ViewGameResult.ViewGameResultViewModel;

public class ViewGameResultInteractor implements ViewGameResultInputBoundary {

    @Override
    public void execute(CurrentGame currentGame) {
        final GameState gameState = currentGame.getGameState();
        if (gameState == GameState.ONGOING) {
            throw new IllegalStateException("Game is still ongoing. Result cannot be viewed.");
        }
        final String gameResult = currentGame.outcome();
        final ArrayList<Card> playerHand = (ArrayList<Card>) currentGame.getPlayerHand();
        final ArrayList<Card> dealerHand = (ArrayList<Card>) currentGame.getDealerHand();

        final int playerScore = calculateScore(playerHand);
        final int dealerScore = calculateScore(dealerHand);

        final ViewGameResultViewModel viewGameResultViewModel = new ViewGameResultViewModel();
        final ViewGameResultPresenter presenter = new ViewGameResultPresenter(viewGameResultViewModel);

        final ViewGameResultOutputData outputData = new ViewGameResultOutputData(currentGame,
                gameResult,
                playerScore,
                dealerScore);
        presenter.presentGameResult(outputData);
    }

    private int calculateScore(ArrayList<Card> hand) {
        int score = 0;
        int numAces = 0;
        for (Card card : hand) {
            final String value = card.getValue();
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
