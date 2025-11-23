package use_case.Stand;

import data_access.DeckAPIInterface;
import entity.Card;
import data_access.Deck;

import java.util.List;

public class StandInteractor implements StandInputBoundary {

    private final StandOutputBoundary presenter;

    public StandInteractor(StandOutputBoundary presenter) {
        this.presenter = presenter;
    }

    @Override
    public void execute(StandInputData inputData) throws DeckAPIInterface.UnableToLoadDeck {
        DeckAPIInterface deck = new Deck();
        int playerTotal = inputData.getPlayerTotal();
        int dealerTotal = 0;

        try {
            // Dealer auto-draws until total >= 17
            while (dealerTotal < 17) {
                List<Card> drawn = deck.drawCards(1);

                if (drawn == null || drawn.isEmpty()) {
                    presenter.presentError("Deck returned no cards.");
                    return;
                }

                Card card = drawn.get(0);
                dealerTotal += cardValue(card);
                presenter.presentDealerDrew(card, dealerTotal);
            }

            String outcome;
            if (playerTotal > 21) {
                outcome = "Dealer wins (player busts)";
            } else if (dealerTotal > 21) {
                outcome = "Player wins (dealer busts)";
            } else if (playerTotal > dealerTotal) {
                outcome = "Player wins";
            } else if (dealerTotal > playerTotal) {
                outcome = "Dealer wins";
            } else {
                outcome = "Push";
            }

            StandOutputData output = new StandOutputData(playerTotal, dealerTotal, outcome);
            presenter.presentResult(output);

        } catch (Deck.UnableToLoadDeck e) {
            presenter.presentError("Could not draw from deck: " + e.getMessage());
        }
    }

    // Convert API value string to Blackjack numeric value
    private int cardValue(Card card) {
        String v = card.getValue();   // "2", "10", "KING", "ACE", etc.

        switch (v) {
            case "KING":
            case "QUEEN":
            case "JACK":
                return 10;
            case "ACE":
                // simple version: ACE = 11
                return 11;
            default:
                return Integer.parseInt(v);
        }
    }
}
