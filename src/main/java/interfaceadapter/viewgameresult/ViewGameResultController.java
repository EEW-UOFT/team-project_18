package interfaceadapter.viewgameresult;

import entity.CurrentGame;
import use.Case.viewgameresult.ViewGameResultInteractor;

public class ViewGameResultController {
    private final ViewGameResultInteractor viewGameResultInteractor;

    public ViewGameResultController(ViewGameResultInteractor viewGameResultInteractor) {
        this.viewGameResultInteractor = viewGameResultInteractor;
    }

    public void execute(CurrentGame currentGame) {
        viewGameResultInteractor.execute(currentGame);
    }
}
