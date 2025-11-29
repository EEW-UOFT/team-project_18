package interfaceadapter.viewgamehistory;
import entity.User;
import use.Case.viewhistory.ViewHistoryInputBoundary;

public class ViewHistoryController {
    private final ViewHistoryInputBoundary interactor;

    public ViewHistoryController(ViewHistoryInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute(User user) {
        interactor.execute(user);
    }
}
