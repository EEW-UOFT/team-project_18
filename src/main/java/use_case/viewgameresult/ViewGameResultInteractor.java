package use_case.viewgameresult;
import entity.Card;
import entity.GameState;
import entity.CurrentGame;
import interface_adapter.ViewGameResult.ViewGameResultPresenter;
import interface_adapter.ViewGameResult.ViewGameResultViewModel;
import interface_adapter.ViewManagerModel;

import java.util.ArrayList;

public class ViewGameResultInteractor implements ViewGameResultInputBoundary {

    private final ViewGameResultPresenter presenter;
    public ViewGameResultInteractor(ViewGameResultPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void execute(CurrentGame currentGame) {
        GameState gameState = currentGame.getGameState();
//        if (gameState == GameState.ONGOING) {
//            throw new IllegalStateException("Game is still ongoing. Result cannot be viewed.");
//        }
        String gameResult = currentGame.outcome();
        ArrayList<Card> playerHand = (ArrayList<Card>) currentGame.getPlayerHand();
        ArrayList<Card> dealerHand = (ArrayList<Card>) currentGame.getDealerHand();

        int playerScore = currentGame.calculateScore(playerHand);
        int dealerScore = currentGame.calculateScore(dealerHand);

        ViewGameResultViewModel viewGameResultViewModel = new ViewGameResultViewModel();
        ViewGameResultPresenter presenter = new ViewGameResultPresenter(viewGameResultViewModel, new ViewManagerModel());

        ViewGameResultOutputData outputData = new ViewGameResultOutputData(currentGame, gameResult, playerScore, dealerScore);
        presenter.presentGameResult(outputData);
    }

}
