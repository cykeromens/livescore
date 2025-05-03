package com.assessment.livescore;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ScoreBoardTest {

    private static final String HOME_TEAM = "Mexico";
    private static final String AWAY_TEAM = "Canada";

    @Test
    void shouldCreateNewMatchWithTeamsAndZeroScore() {
        Match match = new Match(HOME_TEAM, AWAY_TEAM);

        assertEquals(HOME_TEAM, match.getHomeTeam());
        assertEquals(AWAY_TEAM, match.getAwayTeam());
        assertEquals(0, match.getHomeScore());
        assertEquals(0, match.getAwayScore());
    }

    @Test
    void shouldStartMatchAndDisplayOnScoreBoard() {
        ScoreBoard scoreBoard = new ScoreBoard();
        List<Match> scoreBoardBeforeStartingMatch = scoreBoard.getSummary();
        assertEquals(0, scoreBoardBeforeStartingMatch.size());

        scoreBoard.startMatch(HOME_TEAM, AWAY_TEAM);

        List<Match> scoreBoardAfterStartingMatch = scoreBoard.getSummary();

        assertEquals(1, scoreBoardAfterStartingMatch.size());
        assertEquals(HOME_TEAM, scoreBoardAfterStartingMatch.get(0).getHomeTeam());
        assertEquals(AWAY_TEAM, scoreBoardAfterStartingMatch.get(0).getAwayTeam());
    }

    @Test
    void shouldUpdateLiveScoreForMatch() {
        ScoreBoard scoreBoard = new ScoreBoard();
        scoreBoard.startMatch(HOME_TEAM, AWAY_TEAM);

        scoreBoard.updateScore(HOME_TEAM, AWAY_TEAM, 2, 1);

        Match fixture = scoreBoard.getSummary().get(0);
        assertEquals(2, fixture.getHomeScore());
        assertEquals(1, fixture.getAwayScore());
    }

    @Test
    void shouldThrowMatchNotFoundExceptionForMatchNotFound() {
        ScoreBoard scoreBoard = new ScoreBoard();

        MatchNotFoundException matchNotFoundException = assertThrows(MatchNotFoundException.class, () ->
                scoreBoard.updateScore(HOME_TEAM, AWAY_TEAM, 2, 1));

        assertEquals("Match not found!", matchNotFoundException.getMessage());
    }
}
