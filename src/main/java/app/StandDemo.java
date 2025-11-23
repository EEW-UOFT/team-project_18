package app;

import data_access.Deck;
import interface_adapter.Stand.ConsoleStandPresenter;
import interface_adapter.Stand.StandController;
import use_case.Stand.StandInputBoundary;
import use_case.Stand.StandInteractor;

public class StandDemo {

    public static void main(String[] args) {
        try {
            // Initialize shuffled deck from Deck of Cards API
            Deck deck = new Deck();
            deck.initializeNewDeck();

            int fakePlayerTotal = 17; // pretend player stands on 17

            ConsoleStandPresenter presenter = new ConsoleStandPresenter();
            StandInputBoundary interactor = new StandInteractor(presenter);
            StandController controller = new StandController(interactor);

            controller.onStand(deck, fakePlayerTotal);

        } catch (Deck.UnableToLoadDeck e) {
            System.out.println("Failed to initialize deck: " + e.getMessage());
        }
    }
}

