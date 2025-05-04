package com.assessment.livescore;

import com.assessment.livescore.exception.InvalidScoreException;
import com.assessment.livescore.exception.MatchNotFoundException;
import com.assessment.livescore.model.Match;
import com.assessment.livescore.repository.InMemoryMatchRepository;
import com.assessment.livescore.service.ScoreBoardServiceImpl;
import com.assessment.livescore.strategy.DefaultMatchSummaryStrategy;
import com.assessment.livescore.validation.ScoreValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ScoreBoardServiceTest {

    private static final String HOME_TEAM = "Mexico";
    private static final String AWAY_TEAM = "Canada";

    private ScoreBoardService scoreBoardService;

    @BeforeEach
    public void setup(){
        this.scoreBoardService = new ScoreBoardServiceImpl(
                new InMemoryMatchRepository(),
                new ScoreValidator(),
                new DefaultMatchSummaryStrategy()
        );

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
        List<Match> scoreBoardBeforeStartingMatch = scoreBoardService.getScoreSummary();
        assertEquals(0, scoreBoardBeforeStartingMatch.size());

        scoreBoardService.startMatch(HOME_TEAM, AWAY_TEAM);

        List<Match> scoreBoardAfterStartingMatch = scoreBoardService.getScoreSummary();

        assertEquals(1, scoreBoardAfterStartingMatch.size());
        assertEquals(HOME_TEAM, scoreBoardAfterStartingMatch.get(0).getHomeTeam());
        assertEquals(AWAY_TEAM, scoreBoardAfterStartingMatch.get(0).getAwayTeam());
    }

    @Test
    void shouldUpdateLiveScoreForMatch() {
        scoreBoardService.startMatch(HOME_TEAM, AWAY_TEAM);

        scoreBoardService.updateScore(HOME_TEAM, AWAY_TEAM, 2, 1);

        Match fixture = scoreBoardService.getScoreSummary().get(0);
        assertEquals(2, fixture.getHomeScore());
        assertEquals(1, fixture.getAwayScore());
    }

    @Test
    void shouldThrowMatchNotFoundExceptionForMatchNotFound() {
        MatchNotFoundException matchNotFoundException = assertThrows(MatchNotFoundException.class, () ->
                scoreBoardService.updateScore(HOME_TEAM, AWAY_TEAM, 2, 1));

        assertEquals("Match not found!", matchNotFoundException.getMessage());
    }

    @ParameterizedTest
    @CsvSource({
            "-1, 1",
            "-1, -1",
            "1, -1"
    })
    void shouldNotUpdateScoreForScoreLessThanZero(int homeScore, int awayScore) {
        scoreBoardService.startMatch(HOME_TEAM, AWAY_TEAM);

        InvalidScoreException invalidScoreException = assertThrows(InvalidScoreException.class, () ->
                scoreBoardService.updateScore(HOME_TEAM, AWAY_TEAM, homeScore, awayScore));

        assertEquals("Scores must be non-negative!", invalidScoreException.getMessage());
    }

    @Test
    void shouldFinishMatchAndRemoveMatchFromBoard() {
        scoreBoardService.startMatch(HOME_TEAM, AWAY_TEAM);
        assertEquals(1, scoreBoardService.getScoreSummary().size());

        scoreBoardService.finishMatch(HOME_TEAM, AWAY_TEAM);

        assertTrue(scoreBoardService.getScoreSummary().isEmpty());
    }

    @Test
    void shouldThrowMatchNotFoundExceptionForMatchNotOnScoreBoard() {
        MatchNotFoundException matchNotFoundException = assertThrows(MatchNotFoundException.class, () ->
                scoreBoardService.finishMatch(HOME_TEAM, AWAY_TEAM));

        assertEquals("Match not found!", matchNotFoundException.getMessage());
    }

    @Test
    void shouldReturnMatchesOrderedByScoreAndMostRecent() {
        scoreBoardService.startMatch("Mexico","Canada");
        scoreBoardService.updateScore("Mexico","Canada",0,5);
        scoreBoardService.startMatch("Spain","Brazil");
        scoreBoardService.updateScore("Spain","Brazil",10,2);
        scoreBoardService.startMatch("Germany","France");
        scoreBoardService.updateScore("Germany","France",2,2);
        scoreBoardService.startMatch("Uruguay","Italy");
        scoreBoardService.updateScore("Uruguay","Italy",6,6);
        scoreBoardService.startMatch("Argentina","Australia");
        scoreBoardService.updateScore("Argentina","Australia",3,1);

        List<Match> sum = scoreBoardService.getScoreSummary();

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
