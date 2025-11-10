package use_case;

import data_access.DeckGateway;
import entity.Dealer;
import entity.Player;

public class StandInteractor implements StandInputBoundary {
    private final Player player;
    private final Dealer dealer;
    private final DeckGateway deckGateway;
    private final StandOutputBoundary presenter;

    public StandInteractor(Player player, Dealer dealer,
                           DeckGateway deckGateway, StandOutputBoundary presenter) {
        this.player = player;
        this.dealer = dealer;
        this.deckGateway = deckGateway;
        this.presenter = presenter;
    }

    @Override
    public void execute(String deckId) {
        try {
            // Dealer auto-plays
            while (dealer.mustDraw()) {
                String value = deckGateway.drawOneValue(deckId); // "ACE", "7", "KING", ...
                dealer.add(value);
                presenter.presentDealerDrew(value, dealer.total());
            }

            int p = player.total(), d = dealer.total();
            String outcome = (d > 21) ? "Player wins (dealer busts)"
                    : (p > 21) ? "Dealer wins (player busts)"
                    : (p > d)  ? "Player wins"
                    : (p < d)  ? "Dealer wins"
                    :           "Push";
            presenter.presentResult(outcome, p, d);
        } catch (Exception e) {
            presenter.presentError("Stand failed: " + e.getMessage());
        }
    }
}
