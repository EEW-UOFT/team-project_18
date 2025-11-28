package interfaceadapter.stand;

import entity.Card;
import interfaceadapter.ViewManagerModel;
import use.Case.stand.StandOutputBoundary;
import use.Case.stand.StandOutputData;

import java.io.Console;

public class ConsoleStandPresenter implements StandOutputBoundary {

    private final ViewManagerModel viewManagerModel;

    public ConsoleStandPresenter(ViewManagerModel viewManagerModel) {
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(StandOutputData outputData) {

    }

    @Override
    public void prepareFailView(String message) {
        System.out.println("Stand failed: " + message);
    }
}
