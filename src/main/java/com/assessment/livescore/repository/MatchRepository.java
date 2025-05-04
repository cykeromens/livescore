package com.assessment.livescore.repository;

import com.assessment.livescore.model.Match;

import java.util.List;
import java.util.Optional;

public interface MatchRepository {
    void addMatch(Match match);
    Optional<Match> findMatch(String homeTeam, String awayTeam);
    boolean removeMatch(String homeTeam, String awayTeam);
    List<Match> getAllMatches();
}