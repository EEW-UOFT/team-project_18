package entity;


import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserHistoryTest {
    @Test
    public void testUserHistory() {
        List<HistoryEntry> history = new ArrayList<>();
        User user = new User(history);

        HistoryEntry e = new HistoryEntry(10, 15, 22, "Dealer Won");
        user.getGameHistory().add(e);

        assertEquals(1, user.getGameHistory().size());
        assertEquals("Dealer Won", user.getGameHistory().get(0).getOutcome());
    }

}
