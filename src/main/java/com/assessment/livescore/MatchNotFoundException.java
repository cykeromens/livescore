package com.assessment.livescore;

public class MatchNotFoundException extends RuntimeException {

    public MatchNotFoundException() {
        super("Match not found!");
    }

}
