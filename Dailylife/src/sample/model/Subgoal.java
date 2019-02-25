package sample.model;

public class Subgoal {
    private int idg;
    private int id;
    private String name = new String();
    private boolean status;

    public Subgoal(int idg,String name) {
        this.idg = idg;
        this.name = name;
    }

    public Subgoal(int id,int idg,String name,Boolean status) {
        this.id = id;
        this.idg = idg;
        this.name = name;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdg() {
        return idg;
    }

    public void setIdg(int idsg) {
        this.idg = idsg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return name+" "+status;
    }
}