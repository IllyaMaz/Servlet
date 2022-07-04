package model;

public class ProjectsDao {
    private int id;
    private String nameProject;
    private String deadline;
    private Integer cost;

    public ProjectsDao(int id, String nameProject, String deadline, Integer cost){
        this.id=id;
        this.nameProject=nameProject;
        this.deadline=deadline;
        this.cost=cost;
    }

    public ProjectsDao(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameProject() {
        return nameProject;
    }

    public void setNameProject(String nameProject) {
        this.nameProject = nameProject;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }
}
