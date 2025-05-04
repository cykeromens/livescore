package com.assessment.livescore.strategy;

import com.assessment.livescore.model.Match;

import java.util.List;

public interface SummaryStrategy {

    List<Match> order(List<Match> matches);

}
