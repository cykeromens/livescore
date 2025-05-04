package com.assessment.livescore.repository;


import com.assessment.livescore.model.Match;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class InMemoryMatchRepository implements MatchRepository {
    private final List<Match> store = new ArrayList<>();

    @Override public void add(Match match) {
        store.add(match);
    }

    @Override public Optional<Match> find(String home, String away) {
        return store.stream()
            .filter(match -> match.matches(home, away))
            .findFirst();
    }
    @Override public boolean remove(String home, String away) {
        return store.removeIf(match -> match.matches(home, away));
    }
    @Override public List<Match> getAll() {
        return Collections.unmodifiableList(store);
    }
}
