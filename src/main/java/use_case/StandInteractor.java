package use_case;

import data_access.Deck;
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
        Deck deck = inputData.getDeck();
        int playerTotal = inputData.getPlayerTotal();

        try {
            int dealerTotal = 0;

            // Dealer keeps drawing until they reach at least 17
            while (dealerTotal < 17) {
                List<Card> drawn = deck.drawCards(1);  // may throw UnableToLoadDeck

                if (drawn == null || drawn.isEmpty()) {
                    presenter.presentError("Deck returned no cards.");
                    return;
                }

                Card card = drawn.get(0);
                int value = cardValue(card);      // convert String rank -> int
                dealerTotal += value;

                presenter.presentDealerDrew(card, dealerTotal);
            }

            // Decide outcome
            String outcome;
            if (playerTotal > 21 && dealerTotal > 21) {
                // both bust -> call it a push for now
                outcome = "Push";
            } else if (playerTotal > 21) {
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

    /**
     * Convert the card's String value from the API into a Blackjack point value.
     */
    private int cardValue(Card card) {
        String v = card.getValue();  // e.g. "2", "10", "KING", "ACE"

        switch (v) {
            case "KING":
            case "QUEEN":
            case "JACK":
                return 10;
            case "ACE":
                // for now we treat Ace as 11 (your group can refine later)
                return 11;
            default:
                try {
                    return Integer.parseInt(v); // "2".."10"
                } catch (NumberFormatException e) {
                    // if something weird comes back, fail safe with 0
                    return 0;
                }
        }
    }
}
