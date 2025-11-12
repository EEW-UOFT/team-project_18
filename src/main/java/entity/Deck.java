package entity;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.*;

public class Deck {
    int deckID;
    List<Card> drawnCards;
    private final OkHttpClient client = new OkHttpClient();

    private Deck(int deckID){
        //Constructor
        this.deckID = deckID;
    }

    public static class UnableToLoadDeck extends Exception{
        //Exception thrown when API call fails
        public UnableToLoadDeck(){
            super("Unable to load deck");
        }
    }

    public int returnDeckID(){
        //Return deckID
        return deckID;
    }

    public Deck getNewDeck() throws UnableToLoadDeck {
        //Call the API to get a new deck, shuffled
        HttpUrl url = HttpUrl.parse("https://deckofcardsapi.com/api/deck/new/shuffle/?deck_count=1");

        Request request = new Request.Builder().url(url).build();

        try {
            final Response response = client.newCall(request).execute();
            final JSONObject responseBody = new JSONObject(response.body().string());

            if (responseBody.getString("success").equals("true")) {
                deckID = responseBody.getInt("deck_id");
            }
            return new Deck(deckID);
        }
        catch (Exception e) {
            throw new UnableToLoadDeck();
        }
    }

    public List<Card> drawCards(int n) throws UnableToLoadDeck {
        //Calls the API to draws n cards from a deck and returns a list containing them.
        HttpUrl url = HttpUrl.parse("https://deckofcardsapi.com/api/deck").newBuilder()
                .addPathSegment("/"+deckID+"/draw/?count=")
                .addPathSegment(Integer.toString(n))
                .build();

        Request request = new Request.Builder().url(url).build();

        try {
            final Response response = client.newCall(request).execute();
            final JSONObject responseBody = new JSONObject(response.body().string());
            List<Card> currDrawnCards = new ArrayList<>(n);

            if (responseBody.getString("success").equals("true")) {
                JSONArray drawCards = responseBody.getJSONArray("cards");
                for (int i = 0; i < drawCards.length(); i++) {
                    String tempCardSuit = drawCards.getJSONObject(i).getString("suit");
                    String tempCardValue = drawCards.getJSONObject(i).getString("value");
                    Card newCard = new Card(tempCardSuit, tempCardValue);
                    currDrawnCards.add(newCard);
                    drawnCards.add(newCard);
                }
            }
            return currDrawnCards;
        }
        catch (Exception e) {
            throw new UnableToLoadDeck();
        }
    }
}
