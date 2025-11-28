package entity;

public class Card {
    private final String suit;
    private final String value;
    private boolean faceUp;
    private String imageUrl;

    public Card(String suit, String value, String url) {
        this.suit = suit;
        this.value = value;
        this.imageUrl = url;
    }

    public Card(String suit, String value) {
        this.suit = suit;
        this.value = value;
    }

    public String getSuit() {
        return this.suit;
    }

    public boolean isFaceUp() {
        return this.faceUp;
    }

    public void setFaceUp(boolean faceUp) {
        this.faceUp = faceUp;
    }

    public String getValue() {

        return this.value;
    }

    public String getImageUrl() {

        return this.imageUrl;
    }
}
