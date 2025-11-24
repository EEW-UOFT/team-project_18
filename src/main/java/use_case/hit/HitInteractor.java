package use_case.hit;

import data_access.DeckAPIInterface;
import entity.CurrentGame;

public class HitInteractor implements HitInputBoundary{

    private final HitOutputBoundary hitPresenter;

    public HitInteractor(HitOutputBoundary presenter) { this.hitPresenter = presenter; }

    @Override
    public void execute(CurrentGame currentGame) throws DeckAPIInterface.UnableToLoadDeck {
        try{
            currentGame.addCardPlayer(1);
        }
        catch(DeckAPIInterface.UnableToLoadDeck ex){
            this.hitPresenter.prepareFailView("Unable to draw card");
        }

        HitOutputData hitOutputData = new HitOutputData(currentGame);
        hitPresenter.prepareSuccessView(hitOutputData);
    }
}
