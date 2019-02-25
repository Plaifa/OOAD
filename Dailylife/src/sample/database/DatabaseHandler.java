package sample.database;

import sample.model.Goal;
import sample.model.Subgoal;
import sample.model.Task;
import sample.model.User;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class DatabaseHandler extends Configs{
    Connection dbConnection;

    public  Connection getDbConnection() throws ClassNotFoundException, SQLException{
        String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;

        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);
        return dbConnection;
    }

    public void updateTask(Timestamp datecreated, String description, String task, int taskId) throws SQLException, ClassNotFoundException {

        String query = "UPDATE tasks SET datecreated=?, description=?, task=? WHERE taskid=?";

        PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
        preparedStatement.setTimestamp(1, datecreated);
        preparedStatement.setString(2, description);
        preparedStatement.setString(3, task);
        preparedStatement.setInt(4, taskId);
        preparedStatement.executeUpdate();
        preparedStatement.close();

    }

    //write
    public  void signUpUser(User user){
        String insert = "INSERT INTO " + Const.USERS_TABLE+ "(" + Const.USERS_USERNAME+ "," + Const.USERS_EMAIL+ ","
                + Const.USERS_PASSWORD+ "," + Const.USERS_GENDER+ ")" + "VALUES(?,?,?,?)";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = getDbConnection().prepareStatement(insert);

            preparedStatement.setString(1,user.getUsername());
            preparedStatement.setString(2,user.getEmail());
            preparedStatement.setString(3,user.getPassword());
            preparedStatement.setString(4,user.getGender());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public int getAllTask(int userid) throws SQLException, ClassNotFoundException{
        String query = "SELECT COUNT(*) FROM " + Const.TASKS_TABLE + " WHERE " + Const.USERS_ID + "=?";

            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
            preparedStatement.setInt(1,userid);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                return resultSet.getInt(1);
            }

        return resultSet.getInt(1);
    }

    public void addTask(Task task){

        String insert = "INSERT INTO " + Const.TASKS_TABLE+ "(" + Const.USERS_ID+ "," + Const.TASKS_DATE+"," + Const.TASKS_DESCRIPTION+","
                + Const.TASKS_TASK+ ")" + "VALUES(?,?,?,?)";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = getDbConnection().prepareStatement(insert);

            preparedStatement.setInt(1, task.getUserid());
            preparedStatement.setTimestamp(2,task.getDatecreated());
            preparedStatement.setString(3,task.getDescription());
            preparedStatement.setString(4,task.getTask());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getTasksByUser(int userId){
        ResultSet resultTask = null;
        String query = "SELECT * FROM " + Const.TASKS_TABLE+ " WHERE " + Const.USERS_ID+ "=?";

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
            preparedStatement.setInt(1, userId);

            resultTask = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return resultTask;
    }

    // get user information from database to check when user try to log in
    public ResultSet getUser(User user){
        ResultSet resultSet = null;
        if(!user.getUsername().equals("") || !user.getPassword().equals("")){
            String query = "SELECT * FROM " + Const.USERS_TABLE+ " WHERE " + Const.USERS_USERNAME+ "=?" + " AND " + Const.USERS_PASSWORD+"=?";
                    //select all from user table where username="weareone_EXO" and password="weareone"
            try {
                PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
                preparedStatement.setString(1, user.getUsername());
                preparedStatement.setString(2,user.getPassword());

                //return ResultSet = row in database that we ask for
                resultSet = preparedStatement.executeQuery();

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }else{
            System.out.println("Please enter your credential");

        }
        return resultSet;
    }

    //Delete tasks
    public void deleteTask(int userId, int taskId) throws SQLException, ClassNotFoundException{
        String query = "DELETE FROM " + Const.TASKS_TABLE + " WHERE " + Const.USERS_ID + "=?" + " AND " + Const.TASKS_ID + "=?";

        PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
        preparedStatement.setInt(1, userId);
        preparedStatement.setInt(2, taskId);
        preparedStatement.execute();
        preparedStatement.close();
    }

    public String getName(int userId) throws SQLException, ClassNotFoundException {
        System.out.println("yay");
        ResultSet resultUser = null;
        String query = "SELECT * FROM "+ dbName+ "." + Const.USERS_TABLE+ " WHERE " + Const.USERS_ID+ "=?";
        PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
        preparedStatement.setInt(1, userId);

        resultUser = preparedStatement.executeQuery();
        String dbname = null;
        if(resultUser.next()){
            dbname = resultUser.getString("username");
        }
        System.out.println("name from database : " + dbname);
        return dbname;


    }


    //////////////////////////////////////////////////////////////////////////////////////////////////

    private ArrayList<sample.model.Goal> Goal = new ArrayList<>();
    private ArrayList<sample.model.Subgoal> Subgoal = new ArrayList<>();

    public boolean isEmpty(String string) throws SQLException, ClassNotFoundException {
        Statement stmt = getDbConnection().createStatement();
        ResultSet rs = stmt.executeQuery("select * from "+dbName+"."+string);
        String buf = "";
        while(rs.next()) {
            buf = rs.getInt(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4);
            return false;
        }
        return true;
    }

    public LocalDate formatdate(String str){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate dateTime = LocalDate.parse(str, formatter);
        return dateTime;
    }

    public String formatstring(LocalDate localDate){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String dateTime = localDate.format(formatter);
        return dateTime;
    }

    public ArrayList<Goal> getGoal(int id) throws SQLException, ClassNotFoundException {
        String statement = "select * from "+dbName+".Goal where idUser = ?";
        PreparedStatement crucifyPrepareStat = getDbConnection().prepareStatement(statement);
        crucifyPrepareStat.setInt(1, id);

        ResultSet rs = crucifyPrepareStat.executeQuery();

        String buf = "";
        while(rs.next()) {
            //buf = rs.getInt(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4);
            Goal.add(new Goal(rs.getInt(1),rs.getString(2),formatdate(rs.getString(3)),formatdate(rs.getString(4))));
//            System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4));
        }
        return Goal;
    }

    public void insertGoal(String name,String start , String end,int idUser) throws SQLException, ClassNotFoundException {

        String insertQueryStatement = "INSERT  INTO  "+dbName+".Goal (name,datecreate,datedeadline,idUser) VALUES (?,?,?,?)";
        System.out.println(name+start+end+idUser);
        PreparedStatement crucifyPrepareStat = getDbConnection().prepareStatement(insertQueryStatement);
        crucifyPrepareStat.setString(1, name);
        crucifyPrepareStat.setString(2, start);
        crucifyPrepareStat.setString(3, end);
        crucifyPrepareStat.setInt(4, idUser);
        // execute insert SQL statement
        crucifyPrepareStat.executeUpdate();

    }

    public void deleteGoal(int index) throws SQLException, ClassNotFoundException {
        String deleteQueryStatement = "delete from "+dbName+".Goal where idGoal = ?";

        PreparedStatement crucifyPrepareStat = getDbConnection().prepareStatement(deleteQueryStatement);
        crucifyPrepareStat.setInt(1, index);

        // execute insert SQL statement
        crucifyPrepareStat.executeUpdate();
        if(isEmpty("Subgoal")){
            deleteSubgoalG(index);
        }

    }

    public void updateGoal(int index,String name,String start , String end) throws SQLException, ClassNotFoundException {
        String deleteQueryStatement = "UPDATE "+dbName+".Goal SET name=?, datecreate=?, datedeadline=? WHERE idGoal=?";
        PreparedStatement crucifyPrepareStat = getDbConnection().prepareStatement(deleteQueryStatement);
        crucifyPrepareStat.setInt(4, index);
        crucifyPrepareStat.setString(1, name);
        crucifyPrepareStat.setString(2, start);
        crucifyPrepareStat.setString(3, end);
        // execute insert SQL statement
        crucifyPrepareStat.executeUpdate();
    }


    public ArrayList<Subgoal> getSubgoalindex(int id) throws SQLException, ClassNotFoundException {
        String insertQueryStatement = "select * from "+dbName+".Subgoal WHERE idGoal = ?";
        PreparedStatement crucifyPrepareStat = getDbConnection().prepareStatement(insertQueryStatement);
        crucifyPrepareStat.setInt(1, id);

        ResultSet rs = crucifyPrepareStat.executeQuery();
        String buf = "";
        while(rs.next()) {
            //buf = rs.getInt(1) + " " + rs.getInt(2) + " " + rs.getString(3) + " " + rs.getBoolean(4);
            Subgoal.add(new Subgoal(rs.getInt(1),rs.getInt(2),rs.getString(3),rs.getBoolean(4)));
//            System.out.println(rs.getInt(1) + " " + rs.getInt(2) + " " + rs.getString(3) + " " + rs.getBoolean(4));
        }
        return Subgoal;

    }

    public void insertSubgoal(int idgoal ,String name, boolean status) throws SQLException, ClassNotFoundException {

        String insertQueryStatement = "INSERT  INTO  "+dbName+".Subgoal (idGoal,name,status) VALUES  (?,?,?)";

        PreparedStatement crucifyPrepareStat = getDbConnection().prepareStatement(insertQueryStatement);
        crucifyPrepareStat.setInt(1, idgoal);
        crucifyPrepareStat.setString(2, name);
        crucifyPrepareStat.setBoolean(3, status);
        // execute insert SQL statement
        crucifyPrepareStat.executeUpdate();

    }

    public void updateSubgoal(String name,int idSubgoal,int idGoal ,Boolean status) throws SQLException, ClassNotFoundException {
        System.out.println("Update Subgoal");
        String deleteQueryStatement = "UPDATE "+dbName+".Subgoal SET name=? , status=? WHERE idSubgoal=? and idGoal=?";
        PreparedStatement crucifyPrepareStat = getDbConnection().prepareStatement(deleteQueryStatement);
        crucifyPrepareStat.setString(1, name);
        crucifyPrepareStat.setBoolean(2, status);
        crucifyPrepareStat.setInt(3, idSubgoal);
        crucifyPrepareStat.setInt(4, idGoal);
        // execute insert SQL statement
        crucifyPrepareStat.executeUpdate();

    }

    public void deleteSubgoalG(int index) throws SQLException, ClassNotFoundException {

        String deleteQueryStatement = "delete from "+dbName+".SubGoal where idGoal = ?";

        PreparedStatement crucifyPrepareStat = getDbConnection().prepareStatement(deleteQueryStatement);
        crucifyPrepareStat.setInt(1, index);

        // execute insert SQL statement
        crucifyPrepareStat.executeUpdate();
    }

    public void deleteSubgoal(int index) throws SQLException, ClassNotFoundException {
        System.out.println("Delete Subgoal");
        String deleteQueryStatement = "delete from "+dbName+".Subgoal where idSubgoal = ?";

        PreparedStatement crucifyPrepareStat = getDbConnection().prepareStatement(deleteQueryStatement);
        crucifyPrepareStat.setInt(1, index);

        // execute insert SQL statement
        crucifyPrepareStat.executeUpdate();
    }





}
