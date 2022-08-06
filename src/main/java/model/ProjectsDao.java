package model;

public class ProjectsDao {
    private int id;
    private String nameProject;
    private String deadline;
    private String nameCompany;
    private String nameCustomer;

    public ProjectsDao(int id, String nameProject, String deadline, String nameCompany, String nameCustomer){
        this.id = id;
        this.nameProject = nameProject;
        this.deadline = deadline;
        this.nameCustomer = nameCustomer;
        this.nameCompany = nameCompany;
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


    public String getNameCompany() {
        return nameCompany;
    }

    public void setNameCompany(String nameCompany) {
        this.nameCompany = nameCompany;
    }

    public String getNameCustomer() {
        return nameCustomer;
    }

    public void setNameCustomer(String nameCustomer) {
        this.nameCustomer = nameCustomer;
    }
}
