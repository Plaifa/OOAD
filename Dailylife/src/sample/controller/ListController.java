package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.database.DatabaseHandler;
import sample.model.Task;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ListController {

    public static int userId;

    @FXML
    private AnchorPane rootTaskList;

    @FXML
    private ListView<Task> tasklist;

    @FXML
    private Button refreshBtn;

    @FXML
    private Button addBtn;

    @FXML
    private Button logout;

    @FXML
    private ImageView closeBtn;

    private ObservableList<Task> tasks;
    private ObservableList<Task> refreshTasks;

    @FXML
    private Button goalMenu;

    @FXML
    private Label username;

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        goalMenu.setOnAction(event -> {
            try {
                AnchorPane fromPane = FXMLLoader.load(getClass().getResource("/sample/view/Goalpage.fxml"));
                rootTaskList.getChildren().setAll(fromPane);
            } catch (IOException e){
                e.printStackTrace();
            }


        });

        tasks = FXCollections.observableArrayList();

        DatabaseHandler databaseHandler = new DatabaseHandler();
        ResultSet resultSet = databaseHandler.getTasksByUser(ListController.userId);
        while (resultSet.next()) {
            Task task = new Task();
            task.setTaskid(resultSet.getInt("taskid"));
            task.setTask(resultSet.getString("task"));
            task.setDatecreated(resultSet.getTimestamp("datecreated"));
            task.setDescription(resultSet.getString("description"));

            tasks.addAll(task);


        }

        System.out.println("User ID: " + userId);
        username.setText(databaseHandler.getName(userId));

        tasklist.setItems(tasks);
        tasklist.setCellFactory(CellController -> new CellController());

        refreshBtn.setOnMouseClicked(event -> {
            try {
                refreshList();
            } catch (SQLException e){
                e.printStackTrace();
            }
        });

        addBtn.setOnMouseClicked(event -> {
            System.out.println("Add btn clicked");
            System.out.println(this.userId);

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/view/addTaskForm.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));

            stage.showAndWait();

        });

        closeBtn.setOnMouseClicked(event -> {

//            ((Stage) (((Button)event.getSource()).getScene().getWindow())).close();
            Stage st = (Stage)closeBtn.getScene().getWindow();
            st.close();

        });

        /*
        logout.setOnMouseClicked(event -> {
            System.out.println("Logout!");
            //logout.getScene().getWindow().hide();
            Stage st = (Stage) logout.getScene().getWindow();
            st.close();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/view/login.fxml"));

            try{
                loader.load();
            }catch(IOException e){
                e.printStackTrace();
            }

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            stage.showAndWait();
        });
        */
    }

    public void refreshList() throws SQLException {
        System.out.println("Refresh called!");
        System.out.println(this.userId);

        refreshTasks = FXCollections.observableArrayList();

        DatabaseHandler databaseHandler = new DatabaseHandler();
        ResultSet resultSet = databaseHandler.getTasksByUser(ListController.userId);
        while (resultSet.next()) {
            Task task = new Task();
            task.setTaskid(resultSet.getInt("taskid"));
            task.setTask(resultSet.getString("task"));
            task.setDatecreated(resultSet.getTimestamp("datecreated"));
            task.setDescription(resultSet.getString("description"));

            refreshTasks.addAll(task);

        }
        tasklist.setItems(refreshTasks);
        tasklist.setCellFactory(CellController -> new CellController());
    }

    public void setUserId(int id){
        this.userId = id;
        System.out.println("addTask (set) : user id = " + this.userId);
    }

    public int getUserId(){ return this.userId; }





}

