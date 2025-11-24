package interface_adapter.ViewGameResult;

import entity.CurrentGame;
import use_case.ViewGameResult.ViewGameResultOutputData;

public class ViewGameResultPresenter {
    ViewGameResultViewModel viewGameResultViewModel;

    public ViewGameResultPresenter(ViewGameResultViewModel viewGameResultViewModel) {
        this.viewGameResultViewModel = viewGameResultViewModel;
    }

    public void presentGameResult(ViewGameResultOutputData outputData) {
        viewGameResultViewModel.setPlayerScore(outputData.getPlayerScore());
        viewGameResultViewModel.setDealerScore(outputData.getDealerScore());
        viewGameResultViewModel.setCurrentGame(outputData.getCurrentGame());
        viewGameResultViewModel.setGameResult(outputData.getOutcome());
    }
}
