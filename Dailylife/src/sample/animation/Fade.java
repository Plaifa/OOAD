package sample.animation;

import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class Fade {

    FadeTransition fadeTransition;

    public Fade(Node node) {
        fadeTransition = new FadeTransition(Duration.millis(2000),node);

        fadeTransition.setFromValue(1f);
        fadeTransition.setToValue(0f);
        fadeTransition.setCycleCount(1);
        fadeTransition.setAutoReverse(false);
    }
    public void fade(){
        fadeTransition.play();
    }
    public void appear(){
        fadeTransition.setFromValue(0f);
        fadeTransition.setToValue(1f);
    }
}
