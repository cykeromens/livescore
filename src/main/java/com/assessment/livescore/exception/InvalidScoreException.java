package com.assessment.livescore.exception;

public class InvalidScoreException extends RuntimeException {

    public InvalidScoreException() {
        super("Scores must be non-negative!");
    }

}
