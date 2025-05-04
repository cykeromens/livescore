package com.assessment.livescore;

import com.assessment.livescore.exception.InvalidScoreException;
import com.assessment.livescore.exception.MatchNotFoundException;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Slf4j
public class ScoreBoard {

    private final List<Match> matches = new ArrayList<>();

    public void startMatch(String home, String away) {
        Match match = new Match(home, away);
        matches.add(match);
        log.info("Match started: {} vs {}", home, away);
    }

    public List<Match> getSummary() {
        return matches.stream()
                .sorted(Comparator
                        .comparingInt((Match m) -> m.getHomeScore() + m.getAwayScore())
                        .reversed()
                        .thenComparing(m -> -m.getStartTime()))
                .toList();
    }

    public void updateScore(String home, String away, int homeScore, int awayScore) {
        validateScore(homeScore, awayScore);

        Match match = findMatch(home, away)
                .orElseThrow(() -> {
                    log.warn("Update failed: Match not found for {} vs {}", home, away);
                    return new MatchNotFoundException();
                });

        match.setHomeScore(homeScore);
        match.setAwayScore(awayScore);
        log.info("Updated score: {} vs {} => {}:{}", home, away, homeScore, awayScore);
    }

    public void finishMatch(String home, String away) {
        boolean removed = matches.removeIf(m -> m.getHomeTeam().equals(home) && m.getAwayTeam().equals(away));
        if (removed) {
            log.info("Match finished: {} vs {}", home, away);
        }
    }

    private void validateScore(int homeScore, int awayScore) {
        if (homeScore < 0 || awayScore < 0) {
            log.error("Invalid score: {}:{}", homeScore, awayScore);
            throw new InvalidScoreException();
        }
    }

    private Optional<Match> findMatch(String home, String away) {
        return matches.stream()
                .filter(m -> m.getHomeTeam().equals(home) && m.getAwayTeam().equals(away))
                .findFirst();
    }
}
