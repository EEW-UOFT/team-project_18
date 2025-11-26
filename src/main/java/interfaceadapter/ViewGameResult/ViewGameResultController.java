package interfaceadapter.ViewGameResult;

import entity.*;
import use.Case.viewgameresult.ViewGameResultInteractor;

public class ViewGameResultController {
    private ViewGameResultInteractor viewGameResultInteractor;

    public ViewGameResultController() {
        this.viewGameResultInteractor = new ViewGameResultInteractor();
    }

    public void execute(CurrentGame currentGame) {
        viewGameResultInteractor.execute(currentGame);
    }
}
