package com.assessment.livescore.repository;


import com.assessment.livescore.model.Match;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public class InMemoryMatchRepository implements MatchRepository {
    private final Map<String, Match> store = new HashMap<>();

    @Override public void add(Match match) {
        store.put(key(match.getHomeTeam(), match.getAwayTeam()), match);
    }

    @Override
    public Optional<Match> find(String home, String away) {
        return Optional.ofNullable(store.get(key(home, away)));
    }

    @Override
    public boolean remove(String home, String away) {
        return find(home, away)
                .map(m -> store.remove(key(home, away), m))
                .orElse(false);
    }

    @Override
    public List<Match> getAll() {
        return store.values().stream().toList();
    }

    private String key(String homeTeam, String awayTeam){
        return String.join("|", homeTeam, awayTeam);
    }
}


