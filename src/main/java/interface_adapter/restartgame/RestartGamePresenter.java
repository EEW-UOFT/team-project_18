package interface_adapter.restartgame;

import interface_adapter.ViewManagerModel;
import use_case.restartgame.RestartGameOutputBoundary;
import use_case.restartgame.RestartGameOutputData;
import interface_adapter.restartgame.RestartGameViewModel;


public class RestartGamePresenter implements RestartGameOutputBoundary {

    private final RestartGameViewModel restartGameViewModel;
    private final ViewManagerModel viewManagerModel;

    public RestartGamePresenter(RestartGameViewModel restartGameViewModel,
                                ViewManagerModel viewManagerModel) {
        this.restartGameViewModel = restartGameViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(RestartGameOutputData response) {
        restartGameViewModel.setCurrentGame(response.getCurrentGame());
        viewManagerModel.setActiveView("Game");
    }

    @Override
    public void prepareFailView(String errorMessage) {
        restartGameViewModel.setErrorMessage(errorMessage);
    }
}
