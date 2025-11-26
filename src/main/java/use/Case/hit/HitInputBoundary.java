package use.Case.hit;

import data.Access.DeckApiInterface;
import entity.CurrentGame;

public interface HitInputBoundary {

    void execute(CurrentGame currentGame) throws DeckApiInterface.UnableToLoadDeck;
}
