package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.database.DatabaseHandler;
import sample.model.Task;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;

public class CellController extends ListCell<Task> {

    @FXML
    private AnchorPane rootAnchor;

    @FXML
    private ImageView icImgView;

    @FXML
    private Label taskLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private ImageView delBtn;

    @FXML
    private ImageView taskUpdateBtn;

    @FXML
    private Button updateTaskBtn;

    private FXMLLoader fxmlLoader;

    private DatabaseHandler databaseHandler;

    @FXML
    void initialize() {


    }

    @Override
    public void updateItem(Task myTask, boolean empty) throws NullPointerException {
        databaseHandler = new DatabaseHandler();
        super.updateItem(myTask, empty);

        if(empty || myTask == null){
            setText(null);
            setGraphic(null);
        } else {
            if(fxmlLoader == null){
                fxmlLoader = new FXMLLoader(getClass().getResource("/sample/view/cell.fxml"));
                fxmlLoader.setController(this);

                try {
                    fxmlLoader.load();
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }

            taskLabel.setText(myTask.getTask());
            dateLabel.setText(myTask.getDatecreated().toString());
            descriptionLabel.setText(myTask.getDescription());

            int taskId = myTask.getTaskid();

            taskUpdateBtn.setOnMouseClicked(event -> {

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/sample/view/updateTaskForm.fxml"));

                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Parent root = loader.getRoot();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));

                UpdateTaskController updateTaskController = loader.getController();
                updateTaskController.setTaskField(myTask.getTask());
                updateTaskController.setUpdateDescriptionField(myTask.getDescription());

                updateTaskController.updateTaskBtn.setOnAction(event1 -> {
                    Calendar calendar = Calendar.getInstance();
                    java.sql.Timestamp timestamp = new java.sql.Timestamp(calendar.getTimeInMillis());

                    try {
                        System.out.println("taskid " + myTask.getTaskid());
                        databaseHandler.updateTask(timestamp, updateTaskController.getDescription(),
                                updateTaskController.getTask(), myTask.getTaskid());
                        stage.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                });
                stage.show();
            });

            delBtn.setOnMouseClicked(event -> {
                databaseHandler = new DatabaseHandler();
                try {
                    databaseHandler.deleteTask(ListController.userId, taskId);
                } catch (SQLException e){
                    e.printStackTrace();
                } catch (ClassNotFoundException e){
                    e.printStackTrace();
                }
                getListView().getItems().remove(getItem());

            });

            setText(null);
            setGraphic(rootAnchor);
        }
    }
}
