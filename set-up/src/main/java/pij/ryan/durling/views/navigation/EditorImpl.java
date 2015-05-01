package pij.ryan.durling.views.navigation;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import pij.ryan.durling.controllers.QuizCreator;
import pij.ryan.durling.exceptions.IllegalQuizCreationException;
import pij.ryan.durling.exceptions.InvalidQuizException;
import pij.ryan.durling.messages.ViewMessages;
import pij.ryan.durling.models.QuizOption;
import pij.ryan.durling.views.elements.Footer;
import pij.ryan.durling.views.elements.Header;
import pij.ryan.durling.views.elements.ViewBox;
import pij.ryan.durling.views.factories.Views;
import pij.ryan.durling.views.pages.Answers;
import pij.ryan.durling.views.pages.Questions;
import pij.ryan.durling.views.pages.SplitMenu;


public class EditorImpl extends BorderPane implements Editor {

    private Views views;
    private QuizCreator quizCreator;
    private Answers answers;
    private Questions questions;
    private Footer footer;
    private Header header;
    private ViewBox viewBox;

    public EditorImpl() {

    }

    public void setQuizCreator(QuizCreator quizCreator) {
        this.quizCreator = quizCreator;
    }

    public void setViews(Views views) {
        try {
            this.setId(ViewMessages.EDITOR_VIEW_ID);
            String style = ViewMessages.EDITOR_VIEW_STYLE_SHEET;
            this.getStylesheets().add(style);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        this.views = views;
        addHeader();
        addCenter();
        addFooter();
    }

    private void addCenter() {
        viewBox = views.getViewBox();
        viewBox.setView((Node) getSplitMenu(views));
        this.setCenter((Node) viewBox);
    }

    private SplitMenu getSplitMenu(Views views) {
        SplitMenu splitMenu = views.getSplitMenu();
        splitLeft(splitMenu);
        splitRight(splitMenu);
        return splitMenu;
    }

    private void splitRight(SplitMenu splitMenu) {
        int y = 0;
        for(QuizOption option : quizCreator.getClosedQuizzes()) {
            Button button = new Button(option.getQuizTitle());
            button.setOnAction(e -> {
                quizCreator.openQuiz(option.getQuizId());
                addCenter();
            });
            splitMenu.addOptionRight(button, y++);
        }
    }

    private void splitLeft(SplitMenu splitMenu) {
        int y = 0;
        for(QuizOption option : quizCreator.getAvailableQuizzes()) {
            Button button = new Button(option.getQuizTitle());
            button.setOnAction(e -> {
                quizCreator.closeQuiz(option.getQuizId());
                addCenter();
            });
            splitMenu.addOptionLeft(button, y++);
        }
    }

    private void addHeader() {
        header = views.getHeader();
        header.getCreateQuizButton().setOnMousePressed(e -> {
            try {
                quizCreator.createQuiz(header.getTitle());
                viewBox.removeMessage();
                header.setTitle(quizCreator.getName());
                addQuestionView();
            } catch (IllegalArgumentException e1) {
                viewBox.setMessage(e1.getMessage());
            }
        });
        this.setTop((Node) header);
    }

    private void addFooter() {
        footer = views.getFooter();
        footer.getSaveButton().setOnAction(e -> {
            try {
                quizCreator.save();
                viewBox.removeMessage();
                resetEditor();
            } catch (IllegalQuizCreationException | InvalidQuizException e1) {
                viewBox.setMessage(e1.getMessage());
            }
        });
        this.setBottom((Node) footer);
    }


    private void addQuestionView() {
        questions = views.getQuestionView();
        questions.getAddQuestionButton().setOnAction(e -> {
            try {
                quizCreator.addQuestion(questions.getQuestion(), questions.getScore());
                viewBox.removeMessage();
                viewBox.removeView();
                addAnswerView();
            } catch (IllegalQuizCreationException | IllegalArgumentException e1) {
                viewBox.setMessage(e1.getMessage());
            }
        });
        viewBox.setView((Node) questions);
    }

    private void addAnswerView() {
        answers = views.getAnswerView();
        answers.setQuestionLabel(questions.getQuestion());
        viewBox.setView((Node) answers);
        addAnswer();
        addAnotherQuestion();

    }

    private void addAnotherQuestion() {
        answers.getAddAnotherQuestionButton().setOnMousePressed(e -> {
            viewBox.removeView();
            addQuestionView();
            if (quizCreator.validQuiz()) {
                footer.addSaveButton();
            }
        });
    }

    private void addAnswer() {
        answers.getAddAnswerButton().setOnAction(e -> {
            try {
                quizCreator.addAnswer(answers.getAnswer(), answers.getAnswerValue());
                viewBox.removeMessage();
                viewBox.removeView();
                addAnswerView();
            } catch (IllegalQuizCreationException | IllegalArgumentException e1) {
                viewBox.setMessage(e1.getMessage());
            }
        });
    }

    private void resetEditor() {
        addHeader();
        addCenter();
        addFooter();
    }
}