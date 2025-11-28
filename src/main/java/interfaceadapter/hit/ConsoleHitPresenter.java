package interfaceadapter.hit;

import interfaceadapter.GameModel;
import use.Case.hit.HitOutputData;

public class ConsoleHitPresenter {
    private final GameModel gameModel;

    public ConsoleHitPresenter(GameModel gameModel) {
        this.gameModel = gameModel;
    }

    public void hitResult(HitOutputData hitOutputData) {
        gameModel.update(hitOutputData);
    }
}
