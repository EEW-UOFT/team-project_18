package interface_adapter.hit;

import interface_adapter.GameModel;
import use_case.hit.HitOutputData;

public class ConsoleHitPresenter {
    GameModel gameModel;

    public ConsoleHitPresenter(GameModel gameModel) {
        this.gameModel = gameModel;
    }

    public void hitResult(HitOutputData hitOutputData) {
        gameModel.update(hitOutputData);
    }
}
