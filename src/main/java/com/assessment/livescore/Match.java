package com.assessment.livescore;

import com.assessment.livescore.exception.InvalidTeamException;
import lombok.Data;

@Data
public class Match {

    private final long startTime;

    private final String homeTeam;
    private final String awayTeam;
    private int homeScore;
    private int awayScore;

    public Match(String homeTeam, String awayTeam) {
        validateTeams(homeTeam, awayTeam);

        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;

        this.startTime = System.nanoTime();
        this.homeScore = 0;
        this.awayScore = 0;
    }

    private void validateTeams(String homeTeam, String awayTeam) {
        if (isNullOrBlank(homeTeam) || isNullOrBlank(awayTeam)) {
            throw new InvalidTeamException("Home and Away teams must not be null or blank!");
        }

        if (homeTeam.equalsIgnoreCase(awayTeam)) {
            throw new InvalidTeamException("Home and Away teams must be different!");
        }
    }

    private boolean isNullOrBlank(String value) {
        return value == null || value.isBlank();
    }
}
