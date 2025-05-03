package com.assessment.livescore;

import com.assessment.livescore.exception.InvalidScoreException;
import com.assessment.livescore.exception.MatchNotFoundException;

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
        checkIfValidScore(homeScore, awayScore);

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

    private static void checkIfValidScore(int homeScore, int awayScore) {
        if (homeScore < 0 || awayScore < 0) throw new InvalidScoreException();
    }

    public void finishMatch(String home, String away) {
        matches.removeIf(m -> m.getHomeTeam().equals(home) && m.getAwayTeam().equals(away));
    }

}