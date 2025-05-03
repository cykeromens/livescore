package com.assessment.livescore;

public class InvalidScoreException extends RuntimeException {

    public InvalidScoreException() {
        super("Scores must be non-negative!");
    }

}
