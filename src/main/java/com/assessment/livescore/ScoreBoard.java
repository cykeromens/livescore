package com.assessment.livescore;

import com.assessment.livescore.exception.MatchNotFoundException;
import com.assessment.livescore.model.Match;
import com.assessment.livescore.repository.MatchRepository;
import com.assessment.livescore.validation.ScoreValidator;
import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;
import java.util.List;

@Slf4j
public class ScoreBoard {

    private final MatchRepository matchRepository;
    private final ScoreValidator scoreValidator;

    public ScoreBoard(MatchRepository matchRepository, ScoreValidator scoreValidator){
        this.matchRepository = matchRepository;
        this.scoreValidator = scoreValidator;
    }

    public void startMatch(String home, String away) {
        Match match = new Match(home, away);
        matchRepository.add(match);
        log.info("Match started: {} vs {}", home, away);
    }

    public List<Match> getSummary() {
        return matchRepository.getAll()
                .stream()
                .sorted(Comparator
                        .comparingInt((Match m) -> m.getHomeScore() + m.getAwayScore())
                        .reversed()
                        .thenComparing(m -> -m.getStartTime()))
                .toList();
    }

    public void updateScore(String home, String away, int homeScore, int awayScore) {
        scoreValidator.validate(homeScore, awayScore);

        Match match = matchRepository.find(home, away)
                .orElseThrow(() -> {
                    log.warn("Update failed: Match not found for {} vs {}", home, away);
                    return new MatchNotFoundException();
                });

        match.setHomeScore(homeScore);
        match.setAwayScore(awayScore);
        log.info("Updated score: {} vs {} => {}:{}", home, away, homeScore, awayScore);
    }

    public void finishMatch(String home, String away) {
        boolean removed = matchRepository.remove(home, away);
        if (removed) {
            log.info("Match finished: {} vs {}", home, away);
        } else {
            log.warn("Finish failed: Match not found for {} vs {}", home, away);
            throw new MatchNotFoundException();
        }
    }

}
