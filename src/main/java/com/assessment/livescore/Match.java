package com.assessment.livescore;

import lombok.Data;

@Data
public class Match {

    private final long startTime = System.nanoTime();

    private final String homeTeam;
    private final String awayTeam;
    private int homeScore = 0;
    private int awayScore = 0;

    public Match(String home, String away) {
        this.homeTeam = home;
        this.awayTeam = away;
    }
}
