package interface_adapter.ViewGameResult;

import entity.*;
import use_case.ViewGameResult.ViewGameResultInteractor;

public class ViewGameResultController {
    private ViewGameResultInteractor viewGameResultInteractor;

    public ViewGameResultController() {
        this.viewGameResultInteractor = new ViewGameResultInteractor();
    }

    public void execute(CurrentGame currentGame) {
        viewGameResultInteractor.execute(currentGame);
    }
}
