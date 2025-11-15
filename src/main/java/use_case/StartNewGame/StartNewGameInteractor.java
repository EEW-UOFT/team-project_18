package use_case.StartNewGame;

import entity.Card;
import entity.CurrentGame;
import entity.Deck;
import entity.User;
import interface_adapter.StartNewGame.StartNewGamePresenter;

import java.util.List;

public class StartNewGameInteractor implements StartNewGameInputBoundary {

    private CurrentGame currentGame;
    private StartNewGameOutputBoundary startNewGamePresenter;

    public StartNewGameInteractor(StartNewGameOutputBoundary StartNewGamePresenter) {
        this.startNewGamePresenter = StartNewGamePresenter;
    }

    @Override
    public void execute(User currentUser) {
        try {
            Deck deck = new Deck().initializeNewDeck();
            currentGame = new CurrentGame(currentUser, deck);
            List<Card> playerCard = deck.drawCards(2);
            List<Card> dealerCard = deck.drawCards(2);

            currentGame.addCardPlayer(playerCard.get(0));
            currentGame.addCardPlayer(playerCard.get(1));

            dealerCard.get(0).setFaceUp(false);
            currentGame.addCardDealer(dealerCard.get(0));
            dealerCard.get(1).setFaceUp(true);
            currentGame.addCardDealer(dealerCard.get(1));

            final StartNewGameOutputData startNewGameOutputData = new StartNewGameOutputData(currentGame);
            this.startNewGamePresenter.prepareSuccessView(startNewGameOutputData);

        } catch (Deck.UnableToLoadDeck e) {
            this.startNewGamePresenter.prepareFailView("Failed to Start New Game, Please Try Again");
        }
    }
}
