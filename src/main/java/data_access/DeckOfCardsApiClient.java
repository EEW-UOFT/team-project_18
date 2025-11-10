package data_access;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class DeckOfCardsApiClient implements DeckGateway {
    private static final String BASE = "https://deckofcardsapi.com/api/deck";
    private final HttpClient http = HttpClient.newHttpClient();

    @Override
    public String createShuffledShoe(int deckCount) throws Exception {
        String url = BASE + "/new/shuffle/?deck_count=" + deckCount;
        var req = HttpRequest.newBuilder(URI.create(url)).GET().build();
        var res = http.send(req, HttpResponse.BodyHandlers.ofString());
        String body = res.body();
        String key = "\"deck_id\":\"";
        int i = body.indexOf(key);
        if (i < 0) throw new IllegalStateException("No deck_id in response: " + body);
        int start = i + key.length();
        int end = body.indexOf('"', start);
        return body.substring(start, end);
    }

    @Override
    public String drawOneValue(String deckId) throws Exception {
        String url = BASE + "/" + deckId + "/draw/?count=1";
        var req = HttpRequest.newBuilder(URI.create(url)).GET().build();
        var res = http.send(req, HttpResponse.BodyHandlers.ofString());
        String body = res.body();
        String k = "\"value\":\"";
        int i = body.indexOf(k);
        if (i < 0) throw new IllegalStateException("No card value in response: " + body);
        int start = i + k.length();
        int end = body.indexOf('"', start);
        return body.substring(start, end).toUpperCase(); // normalize
    }
}

