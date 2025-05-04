package com.assessment.livescore;

import com.assessment.livescore.exception.InvalidTeamException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class MatchTest {

    @ParameterizedTest
    @CsvSource({
            ", Canada",
            "'' , Canada",
            "Mexico, ",
            "Mexico, ''",
            "'', ''",
            ", ",
    })
    void shouldThrowExceptionWhenTeamIsNullOrEmpty(String homeTeam, String awayTeam) {
        InvalidTeamException invalidTeamException = assertThrows(InvalidTeamException.class,
                () -> new Match(homeTeam, awayTeam));
        assertEquals("Home and Away teams must not be null or blank!", invalidTeamException.getMessage());
    }

}