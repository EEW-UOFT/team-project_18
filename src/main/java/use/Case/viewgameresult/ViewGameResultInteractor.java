package use.Case.viewgameresult;

import entity.Card;
import entity.CurrentGame;
import entity.GameState;
import entity.HistoryEntry;
import interfaceadapter.viewgameresult.ViewGameResultPresenter;
import interfaceadapter.viewgameresult.ViewGameResultViewModel;
import interfaceadapter.viewgameresult.ViewGameResultPresenter;
import interfaceadapter.viewgameresult.ViewGameResultViewModel;

import java.util.ArrayList;

public class ViewGameResultInteractor implements ViewGameResultInputBoundary {

    private final ViewGameResultPresenter presenter;

    public ViewGameResultInteractor(ViewGameResultPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void execute(CurrentGame currentGame) {
        final GameState gameState = currentGame.getGameState();
        if (gameState == GameState.ONGOING) {
            throw new IllegalStateException("Game is still ongoing. Result cannot be viewed.");
        }
        final String gameResult = currentGame.outcome();
        final ArrayList<Card> playerHand = (ArrayList<Card>) currentGame.getPlayerHand();
        final ArrayList<Card> dealerHand = (ArrayList<Card>) currentGame.getDealerHand();

        final int playerScore = currentGame.calculateScore(playerHand);
        final int dealerScore = currentGame.calculateScore(dealerHand);

        HistoryEntry entry = new HistoryEntry(
                (int) (System.currentTimeMillis() / 1000),  // simple timestamp
                playerScore,
                dealerScore,
                gameResult
        );

        currentGame.getPlayer().getGameHistory().add(entry);

        final ViewGameResultViewModel viewGameResultViewModel = new ViewGameResultViewModel();

        final ViewGameResultOutputData outputData = new ViewGameResultOutputData(currentGame,
                gameResult,
                playerScore,
                dealerScore);
        presenter.prepareSuccessView(outputData);
    }

}
