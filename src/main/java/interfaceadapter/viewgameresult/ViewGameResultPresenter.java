package interfaceadapter.viewgameresult;

import use.Case.viewgameresult.ViewGameResultOutputData;

public class ViewGameResultPresenter {
    private ViewGameResultViewModel viewGameResultViewModel;

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
