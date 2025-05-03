package com.assessment.livescore;

import java.util.ArrayList;
import java.util.List;

public class ScoreBoard {

    private final List<Match> matches = new ArrayList<>();

    public void startMatch(String home, String away) {
        matches.add(new Match(home, away));
    }

    public List<Match> getSummary() {
        return new ArrayList<>(matches);
    }
}