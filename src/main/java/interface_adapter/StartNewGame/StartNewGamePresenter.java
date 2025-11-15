package interface_adapter.StartNewGame;

import use_case.StartNewGame.StartNewGameOutputBoundary;
import use_case.StartNewGame.StartNewGameOutputData;

public class StartNewGamePresenter implements StartNewGameOutputBoundary {

    private final StartNewGameViewModel startNewGameViewModel;

    public StartNewGamePresenter(StartNewGameViewModel startNewGameViewModel) {
        this.startNewGameViewModel = startNewGameViewModel;
    }

    @Override
    public void prepareSuccessView(StartNewGameOutputData response){
        startNewGameViewModel.setCurrentGame(response.getCurrentGame());
    }

    @Override
    public void prepareFailView(String errorMessage) {
        startNewGameViewModel.setErrorMessage(errorMessage);

    }

}
