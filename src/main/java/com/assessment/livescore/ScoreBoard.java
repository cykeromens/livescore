package com.assessment.livescore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScoreBoard {

    private final List<Match> matches = new ArrayList<>();

    public void startMatch(String home, String away) {
        matches.add(new Match(home, away));
    }

    public List<Match> getSummary() {
        return Collections.unmodifiableList(matches);
    }

    public void updateScore(String home, String away, int homeScore, int awayScore) {
        for (Match match : matches) {
            if (match.getHomeTeam().equals(home) && match.getAwayTeam().equals(away)) {
                match.setHomeScore(homeScore);
                match.setAwayScore(awayScore);
                return;
            }
        }
        throwMatchNotFoundException();
    }

    private static void throwMatchNotFoundException() {
        throw new MatchNotFoundException();
    }

}