package sample.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import sample.database.DatabaseHandler;

import java.sql.SQLException;
import java.time.LocalDate;

public class AddgoalController {
    DatabaseHandler db = new DatabaseHandler();



    public AddgoalController() throws SQLException {
    }

    public void exit(ActionEvent event){
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }

    public TextField textField;
    public DatePicker datePicker;
    public LocalDate localDate=LocalDate.now();


    public void ok(ActionEvent event) throws SQLException, ClassNotFoundException {
        System.out.println(textField.getText());
        System.out.println(db.formatstring(localDate));
        System.out.println(db.formatstring(datePicker.getValue()));
        System.out.println(ListController.userId);
        db.insertGoal(textField.getText(),db.formatstring(localDate),db.formatstring(datePicker.getValue()),ListController.userId);

        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }


}
