package acceptance.steps.pij.ryan.durling.controllers;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import pij.ryan.durling.controllers.QuizPlayer;
import pij.ryan.durling.controllers.QuizPlayerImpl;
import pij.ryan.durling.models.Question;
import pij.ryan.durling.models.Quiz;
import pij.ryan.durling.models.Score;
import pij.ryan.durling.resources.QuizMaster;
import pij.ryan.durling.resources.ServerLink;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

public class QuizPlayerSteps {

    private QuizPlayer quizPlayer;
    private Quiz quiz;
    private String caught;
    private QuizMaster quizMaster;
    private Score score1;

    @Given("^there is a quiz player$")
    public void there_is_a_quiz_player() throws Throwable {
        ServerLink serverLink = mock(ServerLink.class);
        quizMaster = mock(QuizMaster.class);
        when(serverLink.getQuizMaster()).thenReturn(quizMaster);
        quizPlayer = new QuizPlayerImpl(serverLink);
    }

    @Given("^a player has a menu$")
    public void a_player_has_a_menu() throws Throwable {
        quizPlayer.getMenu();

        verify(quizMaster).getQuizOptions();
    }

    @When("^a player chooses an available quiz (\\d+)$")
    public void a_player_chooses_an_available_quiz(int menuOption) throws Throwable {
        quiz = mock(Quiz.class);
        when(quizMaster.getQuiz(anyInt())).thenReturn(quiz);
        quizPlayer.chooseQuiz(menuOption);

        verify(quizMaster).getQuiz(anyInt());
    }

    @Then("^a player should be able to get the questions for that quiz$")
    public void a_player_should_be_able_to_get_the_questions_for_that_quiz() throws Throwable {
        Set<Question> expected = new HashSet<>();
        when(quiz.getQuestions()).thenReturn(expected);
        Set<Question> actual = quizPlayer.getQuestions();

        verify(quiz).getQuestions();
        assertThat(actual, is(equalTo(expected)));
    }

    @Then("^a player should be able to get the name for that quiz$")
    public void a_player_should_be_able_to_get_the_name_for_that_quiz() throws Throwable {
        String expected = "Bob";
        when(quiz.getName()).thenReturn(expected);
        String actual = quizPlayer.getQuizName();

        verify(quiz).getName();
        assertThat(actual, is(equalTo(expected)));
    }

    @Then("^a player should be able to get there \"([^\"]*)\"$")
    public void a_player_should_be_able_to_get_there(String expected) throws Throwable {
        assertThat(quizPlayer.getPlayerName(), is(equalTo(expected)));
    }

    @When("^a player gives there \"([^\"]*)\"$")
    public void a_player_gives_there(String nameString) throws Throwable {
        String name = ifNull(nameString);
        try {
            quizPlayer.setPlayerName(name);
        } catch (IllegalArgumentException e) {
            caught = e.getMessage();
        }
    }

    @Then("^a player should not be able to get there name$")
    public void a_player_should_not_be_able_to_get_there() throws Throwable {
        assertThat(quizPlayer.getPlayerName(), is(equalTo(null)));
    }

    @And("^should get the message \"([^\"]*)\"$")
    public void should_get_the_message(String message) throws Throwable {
        assertThat(caught, is(equalTo(message)));
    }

    @And("^a player adds (\\d+) to the score$")
    public void a_player_adds_to_the_score(int score) throws Throwable {
        quizPlayer.addToScore(score);
    }

    @Then("^a player should be able to get the (\\d+) for the quiz$")
    public void a_player_should_be_able_to_get_the_for_the_quiz(int score) throws Throwable {
        assertThat(quizPlayer.getScore(), is(equalTo(score)));
    }

    @Then("^the high score should be submitted$")
    public void the_high_score_should_be_submitted() throws Throwable {
        verify(quizMaster).setHighScore(anyInt(), anyString(), anyInt());
    }

    @And("^a player submits the quiz$")
    public void a_player_submits_the_quiz() throws Throwable {
        quizPlayer.submitQuiz();
    }

    @When("^the (\\d+) for the quiz$")
    public void the_for_the_quiz(int current) throws Throwable {
        score1 = mock(Score.class);
        when(quizMaster.getHighScore(anyInt())).thenReturn(score1);
        when(score1.getScore()).thenReturn(current);
    }

    @When("^the players (\\d+) for the quiz$")
    public void the_players_for_the_quiz(int playersScore) throws Throwable {
        quizPlayer.addToScore(playersScore);
    }

    @And("^should be able to get the old players name$")
    public void should_be_able_to_get_the_old_players_name() throws Throwable {
        quizPlayer.getOldCurrentWinner();
        verify(score1).getName();
    }

    @And("^should be able to get the old players score$")
    public void should_be_able_to_get_the_old_players_score() throws Throwable {
        quizPlayer.getOldHighScore();
        verify(score1).getScore();
    }

    @Then("^a player should be able to know if they have \"([^\"]*)\"$")
    public void a_player_should_be_able_to_know_if_they_have(String value) throws Throwable {
        boolean won = ifTrue(value);
        assertThat(quizPlayer.hasWon(), is(equalTo(won)));
    }

    private boolean ifTrue(String argument) {
        boolean value = false;
        String aTrueValue = "true";
        if (aTrueValue.equals(argument)) value = true;
        return value;
    }

    private String ifNull(String argument) {
        String nullValue = "null";
        if (nullValue.equals(argument)) argument = null;
        return argument;
    }
}
