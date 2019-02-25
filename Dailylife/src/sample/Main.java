package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.SQLException;


public class Main extends Application {

    double x ,y;

    public Main() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
    }

    @Override
    public void start(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("view/login.fxml"));
//        Parent root = FXMLLoader.load(getClass().getResource("view/taskview.fxml"));
//        Parent root = FXMLLoader.load(getClass().getResource("view/Goalpage.fxml"));
        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                x = event.getSceneX();
                y = event.getSceneY();
            }
        });

        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - x);
                stage.setY(event.getScreenY() - y);
            }
        });
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setTitle("Todo App");
//        stage.setScene(new Scene(root , 1300, 800));
        stage.setScene(new Scene(root, 400, 600));
        stage.show();


//
//

//

//
//        stage.initStyle(StageStyle.TRANSPARENT);
//        stage.setTitle("Goal");
//        stage.setScene(scene);
////        stage.sizeToScene();
//        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}


