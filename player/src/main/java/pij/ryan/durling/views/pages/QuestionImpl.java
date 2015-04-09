package pij.ryan.durling.views.pages;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import pij.ryan.durling.messages.ViewMessages;

public class QuestionImpl extends GridPane implements Question {

    public QuestionImpl(pij.ryan.durling.models.Question question, GridPane answerView) {
        this.getStylesheets().add(ViewMessages.QUESTION_VIEW_STYLE_SHEET);
        this.setId(ViewMessages.QUESTION_VIEW_ID);
        addQuestion(question.getQuestion());
        addAnswers(answerView);
    }

    public void addAnswers(GridPane answerView) {
        this.add(answerView, 0, 1);
    }

    private void addQuestion(String question) {
        this.add(new Label(question), 0, 0);
    }
}
