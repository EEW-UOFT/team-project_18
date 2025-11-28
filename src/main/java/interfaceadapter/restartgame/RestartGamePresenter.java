package interfaceadapter.restartgame;

import interfaceadapter.ViewManagerModel;
import use.Case.restartgame.RestartGameOutputBoundary;
import use.Case.restartgame.RestartGameOutputData;


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

        restartGameViewModel.firePropertyChanged();

        viewManagerModel.setActiveView("Game");
    }

    @Override
    public void prepareFailView(String errorMessage) {
        restartGameViewModel.setErrorMessage(errorMessage);
    }
}
