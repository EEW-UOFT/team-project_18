package interfaceadapter.startnewgame;

import interfaceadapter.ViewManagerModel;
import use.Case.startnewgame.StartNewGameOutputBoundary;
import use.Case.startnewgame.StartNewGameOutputData;

public class StartNewGamePresenter implements StartNewGameOutputBoundary {

    private final StartNewGameViewModel startNewGameViewModel;
    private final ViewManagerModel viewManagerModel;

    public StartNewGamePresenter(StartNewGameViewModel startNewGameViewModel, ViewManagerModel viewManagerModel) {
        this.startNewGameViewModel = startNewGameViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(StartNewGameOutputData response) {
        startNewGameViewModel.setCurrentGame(response.getCurrentGame());
        startNewGameViewModel.firePropertyChanged();
        viewManagerModel.setActiveView("Game");
    }

    @Override
    public void prepareFailView(String errorMessage) {
        startNewGameViewModel.setErrorMessage(errorMessage);

    }

}
