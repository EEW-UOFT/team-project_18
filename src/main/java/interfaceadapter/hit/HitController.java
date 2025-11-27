package interfaceadapter.hit;

import data.Access.DeckApiInterface;
import entity.CurrentGame;
import use.Case.hit.HitInputBoundary;

public class HitController {

    private final HitInputBoundary hitInputBoundary;

    public HitController(HitInputBoundary hitInputBoundary) {
        this.hitInputBoundary = hitInputBoundary;
    }

    public void onHit(CurrentGame currentGame) throws DeckApiInterface.UnableToLoadDeck {
        hitInputBoundary.execute(currentGame);
    }

}
