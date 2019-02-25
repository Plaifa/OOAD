package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.animation.Shaker;
import sample.database.DatabaseHandler;
import sample.model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class LoginController {
    private int userId;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane login;

    @FXML
    private Button login_loginBtn;

    @FXML
    private PasswordField login_password;

    @FXML
    private Button login_signupBtn;

    @FXML
    private TextField login_username;

    @FXML
    private ImageView closeBtn;

    DatabaseHandler databaseHandler;

    @FXML
    void initialize() {
        databaseHandler = new DatabaseHandler();

        login_loginBtn.setOnAction(event -> {
            String loginTx = login_username.getText().trim();
            String loginPw = login_password.getText().trim();

            User user = new User();
            user.setUsername(loginTx);
            user.setPassword(loginPw);

            ResultSet userRow = databaseHandler.getUser(user);


            int counter = 0;

            try{
                while (userRow.next()){
                    counter++;

                    //print to check the info in userRow
                    String name = userRow.getString("username");
                    //get userId from database
                    userId = userRow.getInt("userid");

                    System.out.println("Welcome " + name + " " + ", ID: " + userId);
                }
                //find a match
                if (counter == 1){
                    System.out.println("login Success");
                    showTasklistScreen();
                }else{
                    // shake animation to give cue to user
                    // that their credentials are wrong
                    Shaker usernameShaker = new Shaker(login_username);
                    Shaker passwordShaker = new Shaker(login_password);
                    usernameShaker.shake();
                    passwordShaker.shake();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }

        });

        closeBtn.setOnMouseClicked(event -> {

//            ((Stage) (((Button)event.getSource()).getScene().getWindow())).close();
            Stage st = (Stage)closeBtn.getScene().getWindow();
            st.close();

        });

        login_signupBtn.setOnAction(event -> {
            try {
                AnchorPane fromPane = FXMLLoader.load(getClass().getResource("/sample/view/signup.fxml"));
                login.getChildren().setAll(fromPane);
            } catch (IOException e){
                e.printStackTrace();
            }

        });

    }

    private void showTasklistScreen(){
        //pass userId to listController class
        ListController listController = new ListController();
        listController.setUserId(userId);

        //hide the current window
        login_signupBtn.getScene().getWindow().hide();

        //load new window
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/view/taskview.fxml"));

        try{
            loader.load();
        }catch(IOException e){
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(new Scene(root));

        stage.showAndWait();
    }


}

