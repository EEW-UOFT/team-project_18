package entity;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private final List<String> values = new ArrayList<>(); // e.g., "10","KING","ACE"

    public void add(String apiValue) { values.add(apiValue); }

    public int total() {
        int sum = 0, aces = 0;
        for (String v : values) {
            switch (v) {
                case "KING": case "QUEEN": case "JACK": sum += 10; break;
                case "ACE": aces++; sum += 11; break;
                default: sum += Integer.parseInt(v); // "2".."10"
            }
        }
        while (sum > 21 && aces > 0) { sum -= 10; aces--; } // make some aces=1
        return sum;
    }
}

