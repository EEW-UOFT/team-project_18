package entity;

import java.util.ArrayList;
import java.util.List;

public class Dealer {
    private final List<String> values = new ArrayList<>();

    public void add(String apiValue) { values.add(apiValue); }

    public int total() {
        int sum = 0, aces = 0;
        for (String v : values) {
            switch (v) {
                case "KING": case "QUEEN": case "JACK": sum += 10; break;
                case "ACE": aces++; sum += 11; break;
                default: sum += Integer.parseInt(v);
            }
        }
        while (sum > 21 && aces > 0) { sum -= 10; aces--; }
        return sum;
    }

    /** Standard: dealer must draw until total >= 17 (stands on soft-17). */
    public boolean mustDraw() { return total() < 17; }
}

