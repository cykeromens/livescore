package com.assessment.livescore.exception;

public class MatchNotFoundException extends RuntimeException {

    public MatchNotFoundException() {
        super("Match not found!");
    }

}
