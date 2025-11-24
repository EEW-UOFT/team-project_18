package interface_adapter.hit;

import data_access.DeckAPIInterface;
import entity.CurrentGame;
import use_case.hit.HitInputBoundary;

public class HitController {

    private final HitInputBoundary hitInputBoundary;

    public HitController(HitInputBoundary hitInputBoundary) {
        this.hitInputBoundary = hitInputBoundary;
    }

    public void onHit(CurrentGame currentGame) throws DeckAPIInterface.UnableToLoadDeck {
        hitInputBoundary.execute(currentGame);
    }

}
