package model;

public class DevelopersDao {
    private Long id;
    private String firstName;
    private Integer age;
    private String gender;
    private Integer salary;
    private String nameProject;

    public DevelopersDao(Long id, String firstName, Integer age, String gender, Integer salary, String nameProject) {
        this.id = id;
        this.firstName = firstName;
        this.age = age;
        this.gender = gender;
        this.salary = salary;
        this.nameProject = nameProject;
    }

    public DevelopersDao() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public String getNameProject() {
        return nameProject;
    }

    public void setNameProject(String nameProject) {
        this.nameProject = nameProject;
    }
}
