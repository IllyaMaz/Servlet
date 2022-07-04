package model;

public class CompaniesDao {
    private long id;
    private String nameCompany;

    public CompaniesDao(long id, String nameCompany){
        this.id=id;
        this.nameCompany=nameCompany;
    }

    public CompaniesDao(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNameCompany() {
        return nameCompany;
    }

    public void setNameCompany(String nameCompany) {
        this.nameCompany = nameCompany;
    }

    @Override
    public String toString() {
        return "CompaniesDao{" +
                "id=" + id +
                ", nameCompany='" + nameCompany + '\'' +
                '}';
    }
}
