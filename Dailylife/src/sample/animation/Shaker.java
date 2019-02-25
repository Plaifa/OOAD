package sample.animation;

import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class Shaker {
    private TranslateTransition translateTransition;

    //node = anything we put inside our stage, scene
    public Shaker(Node node) {
        translateTransition =
                new TranslateTransition(Duration.millis(50), node);
        // f = float, start from the middle
        translateTransition.setFromX(0f);
        //left and right movement
        translateTransition.setByX(10f);
        //how many times it goes back and forth
        translateTransition.setCycleCount(2);
        translateTransition.setAutoReverse(true);
    }

    public void shake(){
        //play animation from the beginning
        translateTransition.playFromStart();
    }
}
