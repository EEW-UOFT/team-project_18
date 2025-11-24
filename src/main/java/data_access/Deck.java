package data_access;

import entity.Card;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import data_access.DeckAPIInterface.UnableToLoadDeck;

public class Deck implements DeckAPIInterface {

    private final OkHttpClient client = new OkHttpClient();

    // deck_id from the API
    private String deckID;

    // all cards drawn so far (optional helper)
    private final List<Card> drawnCards = new ArrayList<>();

    /**
     * Draws n cards from the Deck of Cards API.
     * If deckID is null, it first creates & shuffles a new deck.
     */
    @Override
    public List<Card> drawCards(int n) throws UnableToLoadDeck {
        try {
            // lazily initialize the deck
            if (deckID == null) {
                deckID = initializeNewDeck();
            }

            HttpUrl url = HttpUrl.parse("https://deckofcardsapi.com/api/deck/" + deckID + "/draw/")
                    .newBuilder()
                    .addQueryParameter("count", String.valueOf(n))
                    .build();

            Request request = new Request.Builder().url(url).build();

            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful() || response.body() == null) {
                    throw new UnableToLoadDeck();
                }

                JSONObject body = new JSONObject(response.body().string());

                if (!body.getBoolean("success")) {
                    throw new UnableToLoadDeck();
                }

                JSONArray cardsJson = body.getJSONArray("cards");
                List<Card> result = new ArrayList<>(cardsJson.length());

                for (int i = 0; i < cardsJson.length(); i++) {
                    JSONObject c = cardsJson.getJSONObject(i);
                    String suit = c.getString("suit");   // e.g. "CLUBS"
                    String value = c.getString("value"); // e.g. "4", "KING", "ACE"
                    result.add(new Card(suit, value));
                }

                drawnCards.addAll(result);
                return result;
            }

        } catch (IOException e) {
            throw new UnableToLoadDeck();
        }
    }

    /**
     * Calls the API to create & shuffle a new deck and returns its deck_id.
     */
    private String initializeNewDeck() throws UnableToLoadDeck {
        HttpUrl url = HttpUrl.parse("https://deckofcardsapi.com/api/deck/new/shuffle/")
                .newBuilder()
                .addQueryParameter("deck_count", "1") // can change to "6" later
                .build();

        Request request = new Request.Builder().url(url).build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful() || response.body() == null) {
                throw new UnableToLoadDeck();
            }

            JSONObject body = new JSONObject(response.body().string());

            if (!body.getBoolean("success")) {
                throw new UnableToLoadDeck();
            }

            return body.getString("deck_id");
        } catch (IOException e) {
            throw new UnableToLoadDeck();
        }
    }



    public String getDeckID() {
        return deckID;
    }

    public List<Card> getDrawnCards() {
        return Collections.unmodifiableList(drawnCards);
    }
}
