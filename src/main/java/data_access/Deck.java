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

public class Deck implements DeckAPIInterface {

    private final OkHttpClient client = new OkHttpClient();
    private final List<Card> drawnCards = new ArrayList<>();
    private String deckID;

    @Override
    public List<Card> drawCards(int n) throws UnableToLoadDeck {
        try {
            // lazily initialize deck if needed
            if (deckID == null) {
                initializeNewDeck();
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
                    String suit = c.getString("suit");
                    String value = c.getString("value");
                    result.add(new Card(suit, value));
                }

                drawnCards.addAll(result);
                return result;
            }

        } catch (IOException e) {
            throw new UnableToLoadDeck();
        }
    }

    public void initializeNewDeck() throws UnableToLoadDeck {
        HttpUrl url = HttpUrl.parse("https://deckofcardsapi.com/api/deck/new/shuffle/")
                .newBuilder()
                .addQueryParameter("deck_count", "1")
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

            // ✅ FIXED: STORE DECK ID
            this.deckID = body.getString("deck_id");

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
