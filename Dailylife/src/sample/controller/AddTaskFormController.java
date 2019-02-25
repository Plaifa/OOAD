package sample.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import sample.database.DatabaseHandler;
import sample.model.Task;

import java.sql.SQLException;
import java.util.Calendar;

public class AddTaskFormController {

    DatabaseHandler databaseHandler;

    private int userId;

    @FXML
    private Label successLabel;

    @FXML
    private Button tasksListButton;

    @FXML
    private Button saveTaskBtn;

    @FXML
    private TextField taskField;

    @FXML
    private TextField descriptionField;

    @FXML
    private ImageView closeBtn;

    @FXML
    void initialize() {

        databaseHandler = new DatabaseHandler();

        //Existed tasked
        try {
            tasksListButton.setText("My Tasks (" + databaseHandler.getAllTask(ListController.userId) + ")");
        } catch (SQLException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }

        tasksListButton.setOnAction(event1 -> {
            Stage stage = (Stage) tasksListButton.getScene().getWindow();
            stage.close();
        });

        closeBtn.setOnMouseClicked(event -> {

//            ((Stage) (((Button)event.getSource()).getScene().getWindow())).close();
            Stage st = (Stage)closeBtn.getScene().getWindow();
            st.close();

        });


        saveTaskBtn.setOnAction(event -> {
            Task task = new Task();
            Calendar calendar = Calendar.getInstance();
            java.sql.Timestamp timestamp = new java.sql.Timestamp(calendar.getTimeInMillis());
            String taskText = taskField.getText().trim();
            String taskDescription = descriptionField.getText().trim();

            //get user id from addTaskController
            ListController listController = new ListController();
            setUserId(listController.getUserId());
            userId = getUserId();

            System.out.println("userId : " +userId);


            if(!taskText.equals("") || !taskDescription.equals("")){

                task.setUserid(userId);
                task.setDatecreated(timestamp);
                task.setDescription(taskDescription);
                task.setTask(taskText);
                databaseHandler.addTask(task);

                int taskNo = 0;

                try {
                    taskNo = databaseHandler.getAllTask(ListController.userId);
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

                successLabel.setVisible(true);
                tasksListButton.setText("My Tasks (" + taskNo + ")");
                taskField.setText("");
                descriptionField.setText("");

                System.out.println("Task added successfully! Please refresh your task list");

            }else {
                System.out.println("Nothing add today!");
            }


        });

    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
        System.out.println("addTaskForm (set) : userid = " + this.userId);
    }

}