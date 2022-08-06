package model;

public class CustomersDao {
    private long id;
    private String name;
    private String nameCompany;

    public CustomersDao(long id, String name, String nameCompany){
        this.id=id;
        this.name=name;
        this.nameCompany = nameCompany;
    }

    public CustomersDao(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameCompany() {
        return nameCompany;
    }

    public void setNameCompany(String nameCompany) {
        this.nameCompany = nameCompany;
    }

}
