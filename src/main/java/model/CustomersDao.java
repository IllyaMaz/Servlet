package model;

public class CustomersDao {
    private long id;
    private String name;

    public CustomersDao(long id, String name){
        this.id=id;
        this.name=name;
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

    @Override
    public String toString() {
        return "CustomersDao{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
