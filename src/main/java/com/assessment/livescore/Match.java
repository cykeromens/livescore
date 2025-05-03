package com.assessment.livescore;

public class Match {

    private final String home;
    private final String away;
    private int homeScore = 0;
    private int awayScore = 0;

    public Match(String home, String away) {
        this.home = home; this.away = away;
    }

    public String getHomeTeam() {
        return home;
    }

    public String getAwayTeam() {
        return away;
    }

    public int getHomeScore() {
        return homeScore;
    }

    public int getAwayScore() {
        return awayScore;
    }

    public void setHomeScore(int homeScore) {
        this.homeScore = homeScore;
    }

    public void setAwayScore(int awayScore) {
        this.awayScore = awayScore;
    }
}
