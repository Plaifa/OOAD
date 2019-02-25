package sample.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.database.DatabaseHandler;
import sample.model.Goal;
import sample.model.Subgoal;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class GoalpageController
{
    DatabaseHandler db = new DatabaseHandler();

    public static int idg;

    public static boolean state = true;

    public static ArrayList<Goal> Goal = new ArrayList<>();

    public static ArrayList<Subgoal> Subgoal = new ArrayList<>();

    public static Subgoal sg;

    @FXML
    private AnchorPane rootGoal;

    @FXML
    private Button todoMenu;

    @FXML
    private Button GoalMenu;

    @FXML
    private ComboBox<Goal> comboBox = new ComboBox<>();

    @FXML
    private Label name ;

    @FXML
    private Label date;

    @FXML
    private Button addG;

    @FXML
    private Button addSG;

    @FXML
    private Button edit;

    @FXML
    private Button ok;

    @FXML
    private Button delete;

    @FXML
    private Button refresh;

    @FXML
    private Pane paneedit;

    @FXML
    private Pane paneok;

    @FXML
    private Pane G;

    @FXML
    private VBox vbox;

    @FXML
    private ProgressBar progressgoal;

    @FXML
    private Label percent;

    @FXML
    private TextField nameG;

    @FXML
    private DatePicker deadline;

    @FXML
    private Label username;

    @FXML
    private Label statusgoal;



    double x ,y;

    int count=0;
    public static float per=0;
    public static float per1=0;

    public GoalpageController() throws SQLException {
    }

    public void loadGoal() throws SQLException, ClassNotFoundException {

        //load goal
        per=0;
        per1=0;
        if(!db.isEmpty("Goal")){
//            System.out.println("User id = "+ListController.userId);
            Goal = db.getGoal(2);
            for (int i=0;i<Goal.size();i++){
                comboBox.getItems().add(Goal.get(i));
                //System.out.println(Goal.get(i));
//                if(!db.isEmpty("Subgoal")){
//                    Goal.get(i).addlist(db.getSubgoalindex(Goal.get(i).getId()));
//                }
                //System.out.println(Goal.get(i).getList());
            }
        }
        //System.out.println(Goal);
    }

    public void loadSubgoal(int index) throws SQLException, ClassNotFoundException {
        //load subgoal
        per = 0;
        per1 = 0;
        if (!db.isEmpty("Subgoal")) {
            Subgoal = db.getSubgoalindex(index);
            System.out.println(Subgoal);
            if (Subgoal.size() != 0) {
                for (int i = 0; i < Subgoal.size(); i++) {
                    //                if(index == Subgoal.get(i).getIdg()){
                    sg = Subgoal.get(i);
//                    System.out.println(Subgoal.get(i));
//                    System.out.println(sg);
                    Parent root = null;
                    try {
                        root = FXMLLoader.load(getClass().getResource("../view/Subgoal.fxml"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    vbox.getChildren().add(root);
                    per++;
                    //                    System.out.println(sg);
                    //                    System.out.println(index);
                    if (Subgoal.get(i).getStatus() == true) {
                        per1++;
                    }
                    //                }
                }

//            }
            }
            if (per == 0) {
                per = 1;
            }
            progressgoal.setProgress(per1 / per);
            percent.setText(per1 * 100 / per + "%");
            System.out.println(Subgoal);


        }
    }

    public void showfirst() throws SQLException, ClassNotFoundException {
        if(!db.isEmpty("Goal")){
            progressgoal.setProgress(per1/per);
            percent.setText(per1*100/per+"%");
            comboBox.getSelectionModel().selectFirst();
            name.setText(Goal.get(0).getName());
            date.setText(db.formatstring(Goal.get(0).getDatedeadline()));
//            System.out.println(Goal.get(0).getName());
        }else{
            G.setVisible(false);
        }
    }
    @FXML
    void initialize() throws SQLException, ClassNotFoundException {

        username.setText(db.getName(ListController.userId));
        loadGoal();
        loadSubgoal(Goal.get(0).getId());
        showfirst();
        show(0);

        //show first goal

        comboBox.setOnAction(event -> {
//            G.setVisible(true); //show pane
            vbox.getChildren().clear();

            for (int i=0;i<Goal.size();i++) {
                if (comboBox.getValue().getId() == Goal.get(i).getId()){
                    name.setText(Goal.get(i).getName());
                    date.setText(db.formatstring(Goal.get(i).getDatedeadline()));
                    try {
                        Subgoal.clear();
                        loadSubgoal(comboBox.getValue().getId());
                        show(i);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }


        });

        addG.setOnAction(event ->{
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("/sample/view/AddG.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage stage = new Stage();
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


            Scene scene = new Scene(root);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setTitle("AddG");
            stage.setScene(scene);
            stage.setX(500);
            stage.setY(500);
            stage.showAndWait();
            try {
                Refresh();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        });

        addSG.setOnAction(event -> {
            System.out.println("eiei");
            state=false;
            if(count<7){
                idg = comboBox.getValue().getId();
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("/sample/view/Subgoal.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                vbox.getChildren().add(root);
                count++;
            }
        });

        edit.setOnAction(event ->{
            paneok.setVisible(true);
            paneedit.setVisible(false);
            nameG.setVisible(true);
            deadline.setVisible(true);
            nameG.setText(comboBox.getValue().getName());
            deadline.setValue(comboBox.getValue().getDatedeadline());

        });

        ok.setOnAction(event ->{
            paneedit.setVisible(true);
            paneok.setVisible(false);
            nameG.setVisible(false);
            deadline.setVisible(false);
            try {
                db.updateGoal(comboBox.getValue().getId(),nameG.getText(), db.formatstring(comboBox.getValue().getDatecreate()),db.formatstring(deadline.getValue()));
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            comboBox.getValue().setName(nameG.getText());
            comboBox.getValue().setDatedeadline(deadline.getValue());
            name.setText(nameG.getText());
            date.setText(db.formatstring(deadline.getValue()));
            comboBox.setPromptText(nameG.getText());
        });

        delete.setOnAction(event ->{
            System.out.println("delete");
            try {
                db.deleteGoal(comboBox.getValue().getId());
                Refresh();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            paneedit.setVisible(true);
            paneok.setVisible(false);
            nameG.setVisible(false);
            deadline.setVisible(false);
            //comboBox.getItems().remove(comboBox.getValue().getId());
        });

        refresh.setOnAction(event ->{

            try {
                Refresh();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            //load();
        });

        todoMenu.setOnAction(event ->{
            try {
                AnchorPane fromPane = FXMLLoader.load(getClass().getResource("/sample/view/taskview.fxml"));
                rootGoal.getChildren().setAll(fromPane);
            } catch (IOException e){
                e.printStackTrace();
            }

        });

    }

    public void Refresh() throws SQLException, ClassNotFoundException {

        System.out.println("true");

        Goal.clear();
        System.out.println(Goal);
        Subgoal.clear();
        System.out.println(Subgoal);
        vbox.getChildren().clear();
        comboBox.getItems().clear();
        loadGoal();
        loadSubgoal(Goal.get(0).getId());
        showfirst();
        show(0);
        System.out.println("Refresh success!!");

    }
    public void exit(ActionEvent event){
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }

    public void show(int id){
        System.out.println(Goal.get(id).getDatedeadline());
        System.out.println(Goal.get(id).getDatedeadline().isEqual(LocalDate.now()));
        System.out.println(per1/per*100);

        if((per1/per*100 )< 100 && Goal.get(id).getDatedeadline().isEqual(LocalDate.now())){
            statusgoal.setVisible(true);
            statusgoal.setText("Failed");
        }
        else if((per1/per*100) == 100){
            statusgoal.setVisible(true);
            statusgoal.setText("Success");
        }else{
            statusgoal.setVisible(false);
        }

    }


}
