package com.assessment.livescore.repository;

import com.assessment.livescore.model.Match;

import java.util.List;
import java.util.Optional;

public interface MatchRepository {
    void add(Match match);
    Optional<Match> find(String home, String away);
    boolean remove(String home, String away);
    List<Match> getAll();
}