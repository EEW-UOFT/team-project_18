package interface_adapter.StartNewGame;

import interface_adapter.ViewManagerModel;
import use_case.StartNewGame.StartNewGameOutputBoundary;
import use_case.StartNewGame.StartNewGameOutputData;

public class StartNewGamePresenter implements StartNewGameOutputBoundary {

    private final StartNewGameViewModel startNewGameViewModel;
    private final ViewManagerModel viewManagerModel;

    public StartNewGamePresenter(StartNewGameViewModel startNewGameViewModel, ViewManagerModel viewManagerModel) {
        this.startNewGameViewModel = startNewGameViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(StartNewGameOutputData response){
        startNewGameViewModel.setCurrentGame(response.getCurrentGame());
        viewManagerModel.setActiveView("Game");
    }

    @Override
    public void prepareFailView(String errorMessage) {
        startNewGameViewModel.setErrorMessage(errorMessage);

    }

}
