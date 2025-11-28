package entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardTest {

    @Test
    void testCardCreation() {
        Card card = new Card("HEARTS", "ACE");

        assertEquals("HEARTS", card.getSuit());
        assertEquals("ACE", card.getValue());
    }
}
