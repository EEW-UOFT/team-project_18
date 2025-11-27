package use.Case.hit;

import data.Access.DeckApiInterface;
import entity.CurrentGame;

public class HitInteractor implements HitInputBoundary {

    private final HitOutputBoundary hitPresenter;

    public HitInteractor(HitOutputBoundary presenter) {
        this.hitPresenter = presenter;
    }

    @Override
    public void execute(CurrentGame currentGame) throws DeckApiInterface.UnableToLoadDeck {
        try {
            currentGame.addCardPlayer(1);
        }
        catch (DeckApiInterface.UnableToLoadDeck ex) {
            this.hitPresenter.prepareFailView("Unable to draw card");
        }

        final HitOutputData hitOutputData = new HitOutputData(currentGame);
        hitPresenter.prepareSuccessView(hitOutputData);
    }
}
