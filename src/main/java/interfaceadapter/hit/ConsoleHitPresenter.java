package interfaceadapter.hit;

import interfaceadapter.GameModel;
import use.Case.hit.HitOutputBoundary;
import use.Case.hit.HitOutputData;

public class ConsoleHitPresenter implements HitOutputBoundary {
    private GameModel gameModel;

    public ConsoleHitPresenter(GameModel gameModel) {
        this.gameModel = gameModel;
    }

    @Override
    public void prepareSuccessView(HitOutputData hitOutputData) {
        gameModel.update(hitOutputData);
    }

    @Override
    public void prepareFailView(String error) {
        System.err.println("Hit failed: " + error);
    }
}
