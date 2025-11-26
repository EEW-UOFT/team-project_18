package interface_adapter.ViewGameResult;

import entity.*;
import use_case.viewgameresult.ViewGameResultInteractor;

public class ViewGameResultController {
    private final ViewGameResultInteractor viewGameResultInteractor;

    public ViewGameResultController(ViewGameResultInteractor viewGameResultInteractor) {
        this.viewGameResultInteractor = viewGameResultInteractor;
    }

    public void execute(CurrentGame currentGame) {
        viewGameResultInteractor.execute(currentGame);
    }
}
