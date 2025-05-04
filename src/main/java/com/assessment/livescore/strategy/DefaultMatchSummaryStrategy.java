package com.assessment.livescore.strategy;

import com.assessment.livescore.model.Match;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class DefaultMatchSummaryStrategy implements MatchSummaryStrategy {

    @Override
    public List<Match> order(List<Match> matches) {
        Objects.requireNonNull(matches, "matches must not be null");
        return matches.stream()
                .sorted(
                        Comparator.comparingInt(Match::totalScore)
                                .reversed()
                                .thenComparing(Comparator.comparingLong(Match::getStartTime).reversed())
                )
                .toList();
    }
}