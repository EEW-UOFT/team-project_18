package interfaceadapter.viewgameresult;

import interfaceadapter.ViewManagerModel;
import use.Case.viewgameresult.ViewGameResultOutputBoundary;
import use.Case.viewgameresult.ViewGameResultOutputData;

public class ViewGameResultPresenter implements ViewGameResultOutputBoundary {
    private final ViewManagerModel viewManagerModel;
    private final ViewGameResultViewModel viewGameResultViewModel;


    public ViewGameResultPresenter(ViewGameResultViewModel viewGameResultViewModel, ViewManagerModel viewManagerModel) {
        this.viewManagerModel = viewManagerModel;
        this.viewGameResultViewModel = viewGameResultViewModel;
    }

    @Override
    public void prepareSuccessView(ViewGameResultOutputData outputData) {
        viewGameResultViewModel.setPlayerScore(outputData.getPlayerScore());
        viewGameResultViewModel.setDealerScore(outputData.getDealerScore());
        viewGameResultViewModel.setCurrentGame(outputData.getCurrentGame());
        viewGameResultViewModel.setGameResult(outputData.getOutcome());
        viewManagerModel.setActiveView("GameResult");
    }
}
