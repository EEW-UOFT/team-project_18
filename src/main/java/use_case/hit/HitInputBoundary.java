package use_case.hit;

import data_access.DeckAPIInterface;
import entity.CurrentGame;

public interface HitInputBoundary {

    void execute(CurrentGame currentGame) throws DeckAPIInterface.UnableToLoadDeck;
}
