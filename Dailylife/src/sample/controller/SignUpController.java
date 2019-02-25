package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.database.DatabaseHandler;
import sample.model.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane signup;

    @FXML
    private TextField signup_email;

    @FXML
    private TextField signup_username;

    @FXML
    private TextField signup_password;

    @FXML
    private Button signup_signupBtn;

    @FXML
    private Button signup_loginBtn;

    @FXML
    private CheckBox signup_cbFemale;

    @FXML
    private CheckBox signup_cbMale;

    @FXML
    private ImageView closeBtn;

    @FXML
    void initialize() {

        signup_signupBtn.setOnAction(event ->{
            createUser();
            try {
                AnchorPane fromPane = FXMLLoader.load(getClass().getResource("/sample/view/login.fxml"));
                signup.getChildren().setAll(fromPane);
            } catch (IOException e){
                e.printStackTrace();
            }
        });

        closeBtn.setOnMouseClicked(event -> {

//            ((Stage) (((Button)event.getSource()).getScene().getWindow())).close();
            Stage st = (Stage)closeBtn.getScene().getWindow();
            st.close();

        });

    }

    private void createUser(){

        DatabaseHandler databaseHandler = new DatabaseHandler();

        String name = signup_username.getText();
        String email = signup_email.getText();
        String password = signup_password.getText();

        String gender;

        if(signup_cbFemale.isSelected()){
            gender="female";
        } else gender = "male";

        User user = new User(name, email, password, gender);

        databaseHandler.signUpUser(user);
    }
}