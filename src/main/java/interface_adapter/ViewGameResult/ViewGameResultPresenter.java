package interface_adapter.ViewGameResult;

import interface_adapter.ViewManagerModel;
import use_case.viewgameresult.ViewGameResultOutputData;

public class ViewGameResultPresenter {
    private ViewGameResultViewModel viewGameResultViewModel;
    private final ViewManagerModel viewManagerModel;


    public ViewGameResultPresenter(ViewGameResultViewModel viewGameResultViewModel, ViewManagerModel viewManagerModel) {
        this.viewManagerModel = viewManagerModel;
        this.viewGameResultViewModel = viewGameResultViewModel;
    }

    public void presentGameResult(ViewGameResultOutputData outputData) {
        viewGameResultViewModel.setPlayerScore(outputData.getPlayerScore());
        viewGameResultViewModel.setDealerScore(outputData.getDealerScore());
        viewGameResultViewModel.setCurrentGame(outputData.getCurrentGame());
        viewGameResultViewModel.setGameResult(outputData.getOutcome());
        viewManagerModel.setActiveView("Result");
    }
}
