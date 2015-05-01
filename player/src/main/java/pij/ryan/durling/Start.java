package pij.ryan.durling;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.log4j.BasicConfigurator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pij.ryan.durling.views.navigation.QuizNav;

public class Start extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        BasicConfigurator.configure();
        ApplicationContext context = new ClassPathXmlApplicationContext(
                "classpath:META-INF/spring/applicationContext.xml");
        QuizNav quizNav = context.getBean("quizNav", QuizNav.class);;
        stage.setScene(new Scene((Parent) quizNav, 500, 800));
        stage.show();
    }
}
