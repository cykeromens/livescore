package com.assessment.livescore.service;

import com.assessment.livescore.ScoreBoardService;
import com.assessment.livescore.exception.MatchNotFoundException;
import com.assessment.livescore.model.Match;
import com.assessment.livescore.repository.MatchRepository;
import com.assessment.livescore.strategy.MatchSummaryStrategy;
import com.assessment.livescore.validation.ScoreValidator;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class ScoreBoardServiceImpl implements ScoreBoardService {

    private final MatchRepository matchRepository;
    private final ScoreValidator scoreValidator;
    private final MatchSummaryStrategy matchSummaryStrategy;

    public ScoreBoardServiceImpl(
            MatchRepository matchRepository,
            ScoreValidator scoreValidator,
            MatchSummaryStrategy matchSummaryStrategy
    ){
        this.matchRepository = matchRepository;
        this.scoreValidator = scoreValidator;
        this.matchSummaryStrategy = matchSummaryStrategy;
    }

    public void startMatch(String homeTeam, String awayTeam) {
        Match match = new Match(homeTeam, awayTeam);
        matchRepository.addMatch(match);
        log.info("Match started: {} vs {}", homeTeam, awayTeam);
    }

    public List<Match> getScoreSummary() {
        return matchSummaryStrategy.order(matchRepository.getAllMatches());
    }

    public void updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore) {
        scoreValidator.validate(homeScore, awayScore);

        Match match = matchRepository.findMatch(homeTeam, awayTeam)
                .orElseThrow(() -> {
                    log.warn("Update failed: Match not found for {} vs {}", homeTeam, awayTeam);
                    return new MatchNotFoundException();
                });

        match.updateScore(homeScore, awayScore);
        log.info("Updated score: {} vs {} => {}:{}", homeTeam, awayTeam, homeScore, awayScore);
    }

    public void finishMatch(String homeTeam, String awayTeam) {
        boolean removed = matchRepository.removeMatch(homeTeam, awayTeam);
        if (removed) {
            log.info("Match finished: {} vs {}", homeTeam, awayTeam);
        } else {
            log.warn("Finish failed: Match not found for {} vs {}", homeTeam, awayTeam);
            throw new MatchNotFoundException();
        }
    }

}
