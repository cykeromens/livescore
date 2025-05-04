package com.assessment.livescore.repository;


import com.assessment.livescore.model.Match;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public class InMemoryMatchRepository implements MatchRepository {
    private final Map<String, Match> store = new HashMap<>();

    @Override public void addMatch(Match match) {
        store.put(key(match.getHomeTeam(), match.getAwayTeam()), match);
    }

    @Override
    public Optional<Match> findMatch(String homeTeam, String awayTeam) {
        return Optional.ofNullable(store.get(key(homeTeam, awayTeam)));
    }

    @Override
    public boolean removeMatch(String homeTeam, String awayTeam) {
        return findMatch(homeTeam, awayTeam)
                .map(m -> store.remove(key(homeTeam, awayTeam), m))
                .orElse(false);
    }

    @Override
    public List<Match> getAllMatches() {
        return store.values().stream().toList();
    }

    private String key(String homeTeam, String awayTeam){
        return String.join("|", homeTeam, awayTeam);
    }
}


