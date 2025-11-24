package interface_adapter.Hit;

import data_access.DeckAPIInterface;
import entity.CurrentGame;
import use_case.hit.HitInputBoundary;
import use_case.hit.HitInteractor;
import use_case.stand.StandInputData;

public class HitController {

    private final HitInputBoundary hitInputBoundary;

    public HitController(HitInputBoundary hitInputBoundary) {
        this.hitInputBoundary = hitInputBoundary;
    }

    public void onHit(CurrentGame currentGame) throws DeckAPIInterface.UnableToLoadDeck {
        hitInputBoundary.execute(currentGame);
    }

}
