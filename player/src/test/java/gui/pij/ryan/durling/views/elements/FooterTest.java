package gui.pij.ryan.durling.views.elements;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import org.junit.Test;
import org.loadui.testfx.GuiTest;
import pij.ryan.durling.messages.ViewMessages;
import pij.ryan.durling.views.elements.Footer;
import pij.ryan.durling.views.elements.FooterImpl;

import static org.loadui.testfx.Assertions.verifyThat;
import static org.loadui.testfx.controls.Commons.hasText;

public class FooterTest extends GuiTest {

    private Footer footer;

    @Override
    protected Parent getRootNode() {
        footer = new FooterImpl();
        StackPane stackPane = new StackPane();
        stackPane.setAlignment(Pos.BOTTOM_CENTER);
        stackPane.getChildren().add((Node) footer);
        stackPane.setPrefHeight(700);
        stackPane.setPrefWidth(600);
        stackPane.setPadding(new Insets(50));
        return stackPane;
    }

    @Test
    public void shouldBeAbleToAddASubmitButton() {
        Platform.setImplicitExit(false);
        String submitButton = "#" + ViewMessages.SUBMIT_BUTTON_ID;
        Button button = new Button();
        button.setText("Hello");
        button.setAlignment(Pos.BOTTOM_RIGHT);

        Platform.runLater(() -> {
            footer.setSubmitButton(button);
            verifyThat(submitButton, hasText("Hello"));
        });

    }


}
