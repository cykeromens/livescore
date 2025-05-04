package com.assessment.livescore;

import com.assessment.livescore.exception.InvalidScoreException;
import com.assessment.livescore.exception.MatchNotFoundException;
import com.assessment.livescore.model.Match;
import com.assessment.livescore.repository.InMemoryMatchRepository;
import com.assessment.livescore.repository.MatchRepository;
import com.assessment.livescore.validation.ScoreValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ScoreBoardTest {

    private static final String HOME_TEAM = "Mexico";
    private static final String AWAY_TEAM = "Canada";

    private MatchRepository matchRepository;
    private ScoreValidator scoreValidator;

    @BeforeEach
    public void setup(){
        this.matchRepository = new InMemoryMatchRepository();
        this.scoreValidator = new ScoreValidator();
    }

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
        ScoreBoard scoreBoard = new ScoreBoard(matchRepository, scoreValidator);
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
        ScoreBoard scoreBoard = new ScoreBoard(matchRepository, scoreValidator);
        scoreBoard.startMatch(HOME_TEAM, AWAY_TEAM);

        scoreBoard.updateScore(HOME_TEAM, AWAY_TEAM, 2, 1);

        Match fixture = scoreBoard.getSummary().get(0);
        assertEquals(2, fixture.getHomeScore());
        assertEquals(1, fixture.getAwayScore());
    }

    @Test
    void shouldThrowMatchNotFoundExceptionForMatchNotFound() {
        ScoreBoard scoreBoard = new ScoreBoard(matchRepository, scoreValidator);

        MatchNotFoundException matchNotFoundException = assertThrows(MatchNotFoundException.class, () ->
                scoreBoard.updateScore(HOME_TEAM, AWAY_TEAM, 2, 1));

        assertEquals("Match not found!", matchNotFoundException.getMessage());
    }

    @ParameterizedTest
    @CsvSource({
            "-1, 1",
            "-1, -1",
            "1, -1"
    })
    void shouldNotUpdateScoreForScoreLessThanZero(int homeScore, int awayScore) {
        ScoreBoard scoreBoard = new ScoreBoard(matchRepository, scoreValidator);
        scoreBoard.startMatch(HOME_TEAM, AWAY_TEAM);

        InvalidScoreException invalidScoreException = assertThrows(InvalidScoreException.class, () ->
                scoreBoard.updateScore(HOME_TEAM, AWAY_TEAM, homeScore, awayScore));

        assertEquals("Scores must be non-negative!", invalidScoreException.getMessage());
    }

    @Test
    void shouldFinishMatchAndRemoveMatchFromBoard() {
        ScoreBoard scoreBoard = new ScoreBoard(matchRepository, scoreValidator);
        scoreBoard.startMatch(HOME_TEAM, AWAY_TEAM);
        assertEquals(1, scoreBoard.getSummary().size());

        scoreBoard.finishMatch(HOME_TEAM, AWAY_TEAM);

        assertTrue(scoreBoard.getSummary().isEmpty());
    }

    @Test
    void shouldThrowMatchNotFoundExceptionForMatchNotOnScoreBoard() {
        ScoreBoard scoreBoard = new ScoreBoard(matchRepository, scoreValidator);

        MatchNotFoundException matchNotFoundException = assertThrows(MatchNotFoundException.class, () ->
                scoreBoard.finishMatch(HOME_TEAM, AWAY_TEAM));

        assertEquals("Match not found!", matchNotFoundException.getMessage());
    }

    @Test
    void shouldReturnMatchesOrderedByScoreAndMostRecent() {
        ScoreBoard sb = new ScoreBoard(matchRepository, scoreValidator);
        sb.startMatch("Mexico","Canada"); sb.updateScore("Mexico","Canada",0,5);
        sb.startMatch("Spain","Brazil"); sb.updateScore("Spain","Brazil",10,2);
        sb.startMatch("Germany","France"); sb.updateScore("Germany","France",2,2);
        sb.startMatch("Uruguay","Italy"); sb.updateScore("Uruguay","Italy",6,6);
        sb.startMatch("Argentina","Australia"); sb.updateScore("Argentina","Australia",3,1);

        List<Match> sum = sb.getSummary();

        assertMatch(sum.get(0), "Uruguay", "Italy");
        assertMatch(sum.get(1), "Spain", "Brazil");
        assertMatch(sum.get(2), "Mexico", "Canada");
        assertMatch(sum.get(3), "Argentina", "Australia");
        assertMatch(sum.get(4), "Germany", "France");
    }

    private static void assertMatch(Match match, String home, String away) {
        assertEquals(home, match.getHomeTeam());
        assertEquals(away, match.getAwayTeam());
    }

}
