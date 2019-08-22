package tw.core;/*
 * This Java source file was generated by the Gradle 'init' task.
 */


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tw.core.exception.OutOfGuessCountException;
import tw.core.generator.AnswerGenerator;
import tw.core.model.GuessResult;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GameTest {

    private final Answer actualAnswer = Answer.createAnswer("1 2 3 4");
    private Game game;

    @BeforeEach
    public void setUp() throws Exception {
        AnswerGenerator answerGenerator = mock(AnswerGenerator.class);
        when(answerGenerator.generate()).thenReturn(actualAnswer);
        game = new Game(answerGenerator);
    }

    @Test
    public void should_get_record_of_every_guess_result_when_guess_twice_and_call_guessHistory() throws OutOfGuessCountException {
        //given
        game.guess(Answer.createAnswer("2 1 6 7"));
        game.guess(Answer.createAnswer("1 2 3 4"));

        //when
        List<GuessResult> guessResults = game.guessHistory();

        //then
        assertThat(guessResults.size()).isEqualTo(2);
        assertThat(guessResults.get(0).getResult()).isEqualTo("0A2B");
        assertThat(guessResults.get(0).getInputAnswer().toString()).isEqualTo("2 1 6 7");

        assertThat(guessResults.get(1).getResult()).isEqualTo("4A0B");
        assertThat(guessResults.get(1).getInputAnswer().toString()).isEqualTo("1 2 3 4");
    }

    @Test
    public void should_get_the_success_status_when_guess_input_is_correct() throws Exception {

        //given
        excuteSuccessGuess();
        //when
        String statusOfGame = game.checkStatus();
        //then
        assertThat(statusOfGame).isEqualTo("success");

    }


    @Test
    public void should_get_the_fail_status_when_guess_action_count_over_or_equal_6() throws Exception {

        //given
        excuteSixErrorGuess();
        //when
        String statusOfGame = game.checkStatus();
        //then
        assertThat(statusOfGame).isEqualTo("fail");

    }

    @Test
    public void should_get_the_continue_status_when_guess_action_count_less_than_6() throws Exception {

        //given
        excuteErrorGuessLessThanSixTimes();
        //when
        String statusOfGame = game.checkStatus();
        //then
        assertThat(statusOfGame).isEqualTo("continue");

    }

    @Test
    public void should_get_ture_when_incorrect_guess_action_number_less_than_6() throws Exception {
        //given
        game.guess(Answer.createAnswer("2 1 9 3"));
        //when
        Boolean isContinue = game.checkCoutinue();
        //then
        assertThat(isContinue).isTrue();

    }

    @Test
    public void should_get_ture_when_incorrect_guess_action_number_over_or_equal_6() throws Exception {
        //given
        excuteSixErrorGuess();
        //when
        boolean isContinue = game.checkCoutinue();
        //then
        assertThat(isContinue).isFalse();

    }

    @Test
    public void should_get_false_when_correct_guess() throws Exception {
        //given
        excuteSuccessGuess();
        //when
        boolean isContinue = game.checkCoutinue();
        //then
        assertThat(isContinue).isFalse();

    }

    @Test
    public void should_throw_exception_when_can_not_contiune() throws Exception {
        //given
        excuteSuccessGuess();
        //when
        //then
        assertThrows(OutOfGuessCountException.class, () -> game.guess(Answer.createAnswer("5 2 7 4")));

    }

    private void excuteSuccessGuess() throws OutOfGuessCountException {
        game.guess(Answer.createAnswer("5 2 7 4"));
        game.guess(Answer.createAnswer("1 2 3 4"));
    }

    private void excuteErrorGuessLessThanSixTimes() throws OutOfGuessCountException {
        game.guess(Answer.createAnswer("2 7 3 4"));
        game.guess(Answer.createAnswer("1 5 3 4"));
        game.guess(Answer.createAnswer("1 8 2 1"));
    }

    private void excuteSixErrorGuess() throws OutOfGuessCountException {
        game.guess(Answer.createAnswer("2 9 3 4"));
        game.guess(Answer.createAnswer("1 5 3 4"));
        game.guess(Answer.createAnswer("1 8 2 1"));
        game.guess(Answer.createAnswer("1 2 3 9"));
        game.guess(Answer.createAnswer("4 3 2 1"));
        game.guess(Answer.createAnswer("1 5 6 4"));
    }

}