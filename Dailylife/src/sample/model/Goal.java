package sample.model;


import java.time.LocalDate;
import java.util.ArrayList;

public class Goal {
    private String name = new String();
    private int id;
    private LocalDate Datecreate = LocalDate.now();
    private  LocalDate Datedeadline = null;
    private ArrayList<Subgoal> list = new ArrayList<Subgoal>();

    public Goal(String name, LocalDate datedeadline) {
        this.name = name;
        Datedeadline = datedeadline;
    }

    public Goal(int id,String name,LocalDate datecreate, LocalDate datedeadline) {
        this.id=id;
        this.name = name;
        Datecreate = datecreate;
        Datedeadline = datedeadline;
    }

    public void addlist(Subgoal subgoal){
        list.add(subgoal);
    }

    public void editlist(int index ,Subgoal subgoal){
        dellist(index);
        addlist(subgoal);
    }

    public void dellist(int index){
        list.remove(index);
    }

    public int countlist(){
        return list.size();
    }

    public Subgoal getIndexList(int i){
        return list.get(i);
    }






    //get&set
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDatecreate() {
        return Datecreate;
    }

    public void setDatecreate(LocalDate datecreate) {
        Datecreate = datecreate;
    }

    public LocalDate getDatedeadline() {
        return Datedeadline;
    }

    public void setDatedeadline(LocalDate datedeadline) {
        Datedeadline = datedeadline;
    }

    public ArrayList<Subgoal> getList() {
        return list;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return name;
    }

}
