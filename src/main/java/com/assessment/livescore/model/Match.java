package com.assessment.livescore.model;

import com.assessment.livescore.exception.InvalidTeamException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public final class Match {

    private final long startTime;

    private final String homeTeam;
    private final String awayTeam;
    private int homeScore;
    private int awayScore;

    public Match(String homeTeam, String awayTeam) {
        log.debug("Initialising Match between '{}' and '{}'", homeTeam, awayTeam);

        validateTeams(homeTeam, awayTeam);

        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;

        this.startTime = System.nanoTime();
        this.homeScore = 0;
        this.awayScore = 0;

        log.info("Match created: {} vs {}", homeTeam, awayTeam);
    }

    private void validateTeams(String homeTeam, String awayTeam) {
        if (isNullOrBlank(homeTeam) || isNullOrBlank(awayTeam)) {
            log.error("Validation failed: one or both team names are null or blank");
            throw new InvalidTeamException("Home and Away teams must not be null or blank!");
        }

        if (homeTeam.equalsIgnoreCase(awayTeam)) {
            log.error("Validation failed: teams are the same (ignoring case): {}", homeTeam);
            throw new InvalidTeamException("Home and Away teams must be different!");
        }

        log.debug("Team validation passed for '{}' vs '{}'", homeTeam, awayTeam);
    }

    public void updateScore(int homeScore, int awayScore){
        this.homeScore = homeScore;
        this.awayScore = awayScore;
    }

    private boolean isNullOrBlank(String value) {
        return value == null || value.isBlank();
    }

    public int totalScore() {
        return homeScore + awayScore;
    }

}
