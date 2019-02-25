package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class UpdateTaskController {

    @FXML
    private ImageView closeBtn;

    @FXML
    private TextField updTaskField;

    @FXML
    private TextField updDescriptionField;

    @FXML
    public Button updateTaskBtn;

    @FXML
    void initialize() {
        closeBtn.setOnMouseClicked(event -> {

//            ((Stage) (((Button)event.getSource()).getScene().getWindow())).close();
            Stage st = (Stage)closeBtn.getScene().getWindow();
            st.close();

        });

    }

    public void setTaskField(String task) {
        this.updTaskField.setText(task);
    }

    public String getTask() {
        return this.updTaskField.getText().trim();
    }

    public void setUpdateDescriptionField(String description) {
        this.updDescriptionField.setText(description);
    }

    public String getDescription() {
        return this.updDescriptionField.getText().trim();
    }

    public void refreshList() throws SQLException{
        System.out.println("Calling refresh");

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/view/taskview.fxml"));

        try {
            loader.load();

            ListController listController = new ListController();
            listController.refreshList();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
