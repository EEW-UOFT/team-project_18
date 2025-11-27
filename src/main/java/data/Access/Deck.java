package data.Access;

import java.io.IOException;
import java.util.*;

import org.json.JSONArray;
import org.json.JSONObject;

import entity.Card;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Deck implements DeckApiInterface {
    private String deckID;
    private List<Card> drawnCards;
    private final OkHttpClient client = new OkHttpClient();

    public Deck() throws UnableToLoadDeck {
        this.drawnCards = new ArrayList<>();
        this.deckID = initializeNewDeck();
    }

    public String initializeNewDeck() throws UnableToLoadDeck {

        final HttpUrl url = HttpUrl.parse("https://deckofcardsapi.com/api/deck/new/shuffle/?deck_count=1");

        final Request request = new Request.Builder().url(url).build();
        try {
            final Response response = client.newCall(request).execute();

            final String responseBodyString = response.body().string();

            final JSONObject responseBody = new JSONObject(responseBodyString);

            if (responseBody.getBoolean("success")) {
                final String deckId = responseBody.getString("deck_id");
                return deckId;
            }
            else {
                throw new UnableToLoadDeck();
            }
        }
        catch (IOException e) {
            throw new UnableToLoadDeck();
        }
    }

    @Override
    public List<Card> drawCards(int cardNumber) throws UnableToLoadDeck {

        final HttpUrl url = HttpUrl.parse("https://deckofcardsapi.com/api/deck/" + deckID + "/draw/")
                .newBuilder()
                .addQueryParameter("count", String.valueOf(cardNumber))
                .build();

        final Request request = new Request.Builder().url(url).build();

        try {
            final Response response = client.newCall(request).execute();
            final JSONObject responseBody = new JSONObject(response.body().string());
            final List<Card> currDrawnCards = new ArrayList<>(cardNumber);

            if (responseBody.getBoolean("success")) {
                final JSONArray drawCards = responseBody.getJSONArray("cards");
                for (int i = 0; i < drawCards.length(); i++) {
                    final String tempCardSuit = drawCards.getJSONObject(i).getString("suit");
                    final String tempCardValue = drawCards.getJSONObject(i).getString("value");
                    final String imageUrl = drawCards.getJSONObject(i).getString("image");
                    final Card newCard = new Card(tempCardSuit, tempCardValue, imageUrl);
                    currDrawnCards.add(newCard);
                    drawnCards.add(newCard);
                }
            }
            return currDrawnCards;
        }
        catch (IOException evt) {
            throw new UnableToLoadDeck();
        }
    }
}
