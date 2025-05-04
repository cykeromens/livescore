package com.assessment.livescore.validation;

import com.assessment.livescore.exception.InvalidScoreException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ScoreValidator {

    public void validate(int homeScore, int awayScore) {
        if (homeScore < 0 || awayScore < 0) {
            log.error("Invalid score: {}:{}", homeScore, awayScore);
            throw new InvalidScoreException();
        }
    }
}
