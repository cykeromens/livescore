package com.assessment.livescore.strategy;

import com.assessment.livescore.model.Match;

import java.util.List;

public interface MatchSummaryStrategy {

    List<Match> order(List<Match> matches);

}
