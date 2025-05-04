package com.assessment.livescore;

import com.assessment.livescore.model.Match;

import java.util.List;

public interface ScoreBoardService {
    void startMatch(String homeTeam, String awayTeam);
    void updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore);
    void finishMatch(String homeTeam, String awayTeam);
    List<Match> getSummary();
}
