package use.Case.stand;

import data.Access.Deck;
import data.Access.DeckApiInterface;
import entity.Card;

import java.util.List;

public class StandInteractor implements StandInputBoundary {

    private final StandOutputBoundary presenter;

    public StandInteractor(StandOutputBoundary presenter) {
        this.presenter = presenter;
    }

    @Override
    public void execute(StandInputData inputData) throws DeckApiInterface.UnableToLoadDeck {
        final DeckApiInterface deck = new Deck();
        final int playerTotal = inputData.getPlayerTotal();
        int dealerTotal = 0;

        try {
            // Dealer auto-draws until total >= 17
            while (dealerTotal < 17) {
                final List<Card> drawn = deck.drawCards(1);

                if (drawn == null || drawn.isEmpty()) {
                    presenter.presentError("Deck returned no cards.");
                    return;
                }

                final Card card = drawn.get(0);
                dealerTotal += cardValue(card);
                presenter.presentDealerDrew(card, dealerTotal);
            }

            final String outcome;
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

            final StandOutputData output = new StandOutputData(playerTotal, dealerTotal, outcome);
            presenter.presentResult(output);

        } catch (Deck.UnableToLoadDeck e) {
            presenter.presentError("Could not draw from deck: " + e.getMessage());
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
