import data_access.DeckGateway;
import data_access.DeckOfCardsApiClient;
import interface_adapter.ConsolePresenter;
import interface_adapter.StandController;
import use_case.StandInputBoundary;
import use_case.StandInteractor;
import use_case.StandOutputBoundary;

public class StandDemo {
    public static void main(String[] args) throws Exception {
        // 1. Create API client
        DeckGateway api = new DeckOfCardsApiClient();

        // 2. Create a new shuffled shoe (e.g., 6 decks for Blackjack)
        String deckId = api.createShuffledShoe(6);

        // 3. Create player and dealer
        Player player = new Player();
        Dealer dealer = new Dealer();

        // 4. Give the player some starting cards (simulate previous game steps)
        // (you can either draw from the API or hardcode test values)
        player.add("10");
        player.add("7"); // player total = 17

        // 5. Set up presenter + interactor + controller for Stand use case
        StandOutputBoundary presenter = new ConsolePresenter();
        StandInputBoundary interactor = new StandInteractor(player, dealer, api, presenter);
        StandController controller = new StandController(interactor);

        // 6. Call your use case: this is what you'd do when the player clicks "Stand"
        controller.onStand(deckId);
    }
}

