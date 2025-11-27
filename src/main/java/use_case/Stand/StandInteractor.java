package use_case.Stand;

import data_access.DeckAPIInterface;
import entity.Card;

import java.util.List;

public class StandInteractor implements StandInputBoundary {

    private final StandOutputBoundary presenter;

    public StandInteractor(StandOutputBoundary presenter) {
        this.presenter = presenter;
    }

    @Override
    public void execute(StandInputData inputData) {
        int playerTotal = inputData.getPlayerTotal();
        DeckAPIInterface deck = inputData.getDeck();

        int dealerTotal = 0;

        try {
            // Dealer hits until total >= 17
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

            StandOutputData outputData =
                    new StandOutputData(playerTotal, dealerTotal, outcome);
            presenter.presentResult(outputData);

        } catch (DeckAPIInterface.UnableToLoadDeck e) {
            presenter.presentError("Failed to draw from deck: " + e.getMessage());
        }
    }

    // Convert the Card's "value" field from the API into a blackjack value.
    private int cardValue(Card card) {
        String v = card.getValue();  // "2", "10", "KING", "ACE", ...

        switch (v) {
            case "KING":
            case "QUEEN":
            case "JACK":
                return 10;
            case "ACE":
                // basic version: always 11
                return 11;
            default:
                return Integer.parseInt(v);
        }
    }
}
