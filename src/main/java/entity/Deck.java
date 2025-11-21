package entity;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Deck {

    private String deckID;
    private final List<Card> drawnCards;
    private final OkHttpClient client = new OkHttpClient();

    public Deck() {
        // Keep track of all cards that have been drawn from this deck
        this.drawnCards = new ArrayList<>();
    }

    // Exception thrown when any API-related operation fails
    public static class UnableToLoadDeck extends Exception {
        public UnableToLoadDeck() {
            super("Unable to load deck");
        }

        public UnableToLoadDeck(String message) {
            super(message);
        }
    }

    /**
     * Returns the current deck ID (assigned by the Deck of Cards API).
     */
    public String returnDeckID() {
        return deckID;
    }

    /**
     * Calls the Deck of Cards API to create a new shuffled deck.
     * On success, sets this.deckID and returns this Deck instance.
     */
    public Deck initializeNewDeck() throws UnableToLoadDeck {
        HttpUrl url = HttpUrl.parse(
                "https://deckofcardsapi.com/api/deck/new/shuffle/?deck_count=1"
        );

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.body() == null) {
                throw new UnableToLoadDeck("Empty response body from deck API");
            }

            JSONObject responseBody = new JSONObject(response.body().string());

            // ✅ BUG FIX: success is a boolean, not a string
            if (responseBody.getBoolean("success")) {
                this.deckID = responseBody.getString("deck_id");
                return this;
            } else {
                throw new UnableToLoadDeck("Deck API returned success = false");
            }
        } catch (Exception e) {
            throw new UnableToLoadDeck(e.getMessage());
        }
    }

    /**
     * Draws n cards from the current deck via the API and returns them as a list of Card entities.
     * Also appends them to this.drawnCards.
     */
    public List<Card> drawCards(int n) throws UnableToLoadDeck {
        if (deckID == null) {
            throw new UnableToLoadDeck("Deck ID is null. Call initializeNewDeck() first.");
        }

        HttpUrl url = HttpUrl.parse("https://deckofcardsapi.com/api/deck/" + deckID + "/draw/")
                .newBuilder()
                .addQueryParameter("count", String.valueOf(n))
                .build();

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.body() == null) {
                throw new UnableToLoadDeck("Empty response body when drawing cards");
            }

            JSONObject responseBody = new JSONObject(response.body().string());
            List<Card> currDrawnCards = new ArrayList<>(n);

            // ✅ BUG FIX HERE TOO: success is boolean
            if (responseBody.getBoolean("success")) {
                JSONArray drawCards = responseBody.getJSONArray("cards");

                for (int i = 0; i < drawCards.length(); i++) {
                    JSONObject cardJson = drawCards.getJSONObject(i);
                    String suit = cardJson.getString("suit");   // e.g. "HEARTS"
                    String value = cardJson.getString("value"); // e.g. "8", "KING", "ACE"

                    Card newCard = new Card(suit, value);
                    currDrawnCards.add(newCard);
                    drawnCards.add(newCard);
                }
            } else {
                throw new UnableToLoadDeck("Deck API returned success = false when drawing cards");
            }

            return currDrawnCards;
        } catch (Exception e) {
            throw new UnableToLoadDeck(e.getMessage());
        }
    }

    /**
     * Optional: returns all cards drawn so far from this deck.
     */
    public List<Card> getDrawnCards() {
        return new ArrayList<>(drawnCards);
    }
}
