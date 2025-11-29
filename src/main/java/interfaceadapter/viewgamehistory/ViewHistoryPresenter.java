package interfaceadapter.viewgamehistory;
import use.Case.viewhistory.ViewHistoryOutputBoundary;
import use.Case.viewhistory.ViewHistoryOutputData;

public class ViewHistoryPresenter implements ViewHistoryOutputBoundary {
    private final ViewHistoryViewModel viewModel;

    public ViewHistoryPresenter(ViewHistoryViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void presentHistory(ViewHistoryOutputData outputData) {
        viewModel.setHistory(outputData.getEntries());
    }
}
