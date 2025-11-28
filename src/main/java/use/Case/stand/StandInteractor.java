package use.Case.stand;

import java.util.List;

import data.Access.Deck;
import data.Access.DeckApiInterface;
import entity.Card;
import entity.CurrentGame;

public class StandInteractor implements StandInputBoundary {

    private final StandOutputBoundary presenter;

    public StandInteractor(StandOutputBoundary presenter) {
        this.presenter = presenter;
    }

    @Override
    public void execute(CurrentGame currentGame) throws DeckApiInterface.UnableToLoadDeck {

        try {

            int dealerTotal = currentGame.calculateScore(currentGame.getDealerHand());

            // Dealer auto-draws until total >= 17
            while (dealerTotal < 17) {
                currentGame.addCardDealer(1, true);
                dealerTotal = currentGame.calculateScore(currentGame.getDealerHand());
            }

            int playerTotal = currentGame.calculateScore(currentGame.getPlayerHand());

            final String outcome;
            if (playerTotal > 21) {
                outcome = "Dealer wins (player busts)";
                currentGame.gameLost();
            } else if (dealerTotal > 21) {
                outcome = "Player wins (dealer busts)";
                currentGame.gameWon();
            } else if (playerTotal > dealerTotal) {
                outcome = "Player wins";
                currentGame.gameWon();
            } else if (dealerTotal > playerTotal) {
                outcome = "Dealer wins";
                currentGame.gameLost();
            } else {
                outcome = "Push";
                currentGame.gameDraw();
            }

            final StandOutputData output = new StandOutputData(currentGame, outcome);
            presenter.prepareSuccessView(output);

        } catch (Deck.UnableToLoadDeck e) {
            presenter.prepareFailView("Could not draw from deck: " + e.getMessage());
        }
    }

    private int cardValue(Card card) {
        final String value = card.getValue();

        switch (value) {
            case "KING":
            case "QUEEN":
            case "JACK":
                return 10;
            case "ACE":
                // simple version: ACE = 11
                return 11;
            default:
                return Integer.parseInt(value);
        }
    }
}
