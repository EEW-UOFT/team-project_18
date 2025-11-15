package entity;

public class Card {
    private final String suit;
    private final String value;
    private boolean faceUp;

    public Card(String suit, String value) {
        this.suit = suit;
        this.value = value;
    }

    public String getSuit() {
        return this.suit;
    }

    public void setFaceUp(boolean faceUp){ this.faceUp = faceUp; }

    public boolean isFaceUp(){ return this.faceUp; }

    public String getValue() {
        return this.value;
    }
}
