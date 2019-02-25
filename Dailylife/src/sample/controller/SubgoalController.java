package sample.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import sample.database.DatabaseHandler;
import sample.model.Subgoal;

import java.sql.SQLException;

public class SubgoalController extends ListCell<Subgoal>{

    DatabaseHandler db = new DatabaseHandler();
    private Subgoal sg = GoalpageController.sg;

    @FXML
    private CheckBox cb;

    @FXML
    private TextField nameSG;

    @FXML
    private Pane paneedit;

    @FXML
    private Button edit;

    @FXML
    private Pane paneok;

    @FXML
    private Button ok;

    @FXML
    private Pane panedelete;

    @FXML
    private Button delete;

    @FXML
    private AnchorPane ap;

    public SubgoalController() throws SQLException {
    }


    @FXML
    void initialize() throws SQLException {


        cb.setOnAction(event -> {
            if(cb.isSelected()){
                System.out.println("checkbox is true");
                System.out.println(sg);
                cb.setSelected(true);
                sg.setStatus(true);

                try {

                    db.updateSubgoal(sg.getName(),sg.getId(),sg.getIdg(),sg.getStatus());
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }else{
                System.out.println("checkbox is false");
                System.out.println(sg);
                cb.setSelected(false);
                sg.setStatus(false);

                try {
                    db.updateSubgoal(sg.getName(),sg.getId(),sg.getIdg(),sg.getStatus());
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }

        });

        if(GoalpageController.state==true)
        {

            //nameSG.setText(sg.getName());
            cb.setText(sg.getName());
            if(sg.getStatus()==true){
                cb.setSelected(true);
            }
            edit.setOnAction(event -> {
                paneedit.setVisible(false);
                nameSG.setVisible(true);
                paneok.setVisible(true);
                panedelete.setVisible(true);
                nameSG.setText(cb.getText());

            });
            ok.setOnAction(event -> {
                paneedit.setVisible(true);
                nameSG.setVisible(false);
                paneok.setVisible(false);
                panedelete.setVisible(false);
                try {
                    db.updateSubgoal(nameSG.getText(),sg.getId(),sg.getIdg(),sg.getStatus());
                    sg.setName(nameSG.getText());
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                cb.setText(nameSG.getText());
                nameSG.setText(nameSG.getText());

            });
            delete.setOnAction(event -> {
                System.out.println("delete");
                try {
                    db.deleteSubgoal(sg.getId());
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                ap.getChildren().clear();
                ap.setPrefSize(0,0);
            });
        }else{
//            System.out.println("add from app");
            //nameSG.setText(sg.getName());
            cb.setText(sg.getName());
            GoalpageController.state=true;
            paneedit.setVisible(false);
            nameSG.setVisible(true);
            paneok.setVisible(true);
            panedelete.setVisible(true);

            edit.setOnAction(event -> {
                paneedit.setVisible(false);
                nameSG.setVisible(true);
                paneok.setVisible(true);
                panedelete.setVisible(true);
                nameSG.setText(cb.getText());

            });
            ok.setOnAction(event -> {
                paneedit.setVisible(true);
                nameSG.setVisible(false);
                paneok.setVisible(false);
                panedelete.setVisible(false);
                try {
                    db.insertSubgoal(GoalpageController.idg,nameSG.getText(),false);
                    sg.setName(nameSG.getText());
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                cb.setText(nameSG.getText());
                nameSG.setText(nameSG.getText());

            });
            delete.setOnAction(event -> {
                System.out.println("delete");
                try {
                    db.deleteSubgoal(sg.getId());
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                ap.getChildren().clear();
                ap.setPrefSize(0,0);
            });
        }
    }







}
