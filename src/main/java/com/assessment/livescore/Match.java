package com.assessment.livescore;

import com.assessment.livescore.exception.InvalidTeamException;
import lombok.Data;

@Data
public class Match {

    private final long startTime = System.nanoTime();

    private final String homeTeam;
    private final String awayTeam;
    private int homeScore = 0;
    private int awayScore = 0;

    public Match(String homeTeam, String awayTeam) {
        validateTeams(homeTeam, awayTeam);

        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
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
