package interfaceadapter.viewgameresult;

import use.Case.viewgameresult.ViewGameResultOutputBoundary;
import use.Case.viewgameresult.ViewGameResultOutputData;
import interfaceadapter.ViewManagerModel;

public class ViewGameResultPresenter implements ViewGameResultOutputBoundary {
    private ViewGameResultViewModel viewGameResultViewModel;
    private final ViewManagerModel viewManagerModel;


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
