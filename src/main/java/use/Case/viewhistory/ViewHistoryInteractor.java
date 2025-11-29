package use.Case.viewhistory;

import entity.User;

public class ViewHistoryInteractor implements ViewHistoryInputBoundary {
    private final ViewHistoryOutputBoundary presenter;

    public ViewHistoryInteractor(ViewHistoryOutputBoundary presenter) {
        this.presenter = presenter;
    }

    public void execute(User user) {
        presenter.presentHistory(
                new ViewHistoryOutputData(user.getGameHistory())
        );
    }
}
