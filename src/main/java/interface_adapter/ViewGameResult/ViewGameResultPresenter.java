package interface_adapter.ViewGameResult;

import interface_adapter.ViewManagerModel;
import use_case.viewgameresult.ViewGameResultOutputData;

public class ViewGameResultPresenter {
    private ViewGameResultViewModel viewGameResultViewModel;
    private final ViewManagerModel viewManagerModel;


    public ViewGameResultPresenter(ViewGameResultViewModel viewGameResultViewModel, ViewManagerModel viewManagerModel) {
        this.viewManagerModel = viewManagerModel;
        this.viewGameResultViewModel = viewGameResultViewModel;
        System.out.println("viewManagerModel hash in presenter constructor:");
        System.out.println(System.identityHashCode(viewManagerModel));
    }

    public void presentGameResult(ViewGameResultOutputData outputData) {
        viewGameResultViewModel.setPlayerScore(outputData.getPlayerScore());
        viewGameResultViewModel.setDealerScore(outputData.getDealerScore());
        viewGameResultViewModel.setCurrentGame(outputData.getCurrentGame());
        viewGameResultViewModel.setGameResult(outputData.getOutcome());
        viewManagerModel.setActiveView("Result");
        System.out.println("viewManagerModel hash in presenter presentGameResult:");
        System.out.println(System.identityHashCode(viewManagerModel));
    }
}
