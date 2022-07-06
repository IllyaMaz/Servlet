package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Driver {

    private final String url;
    private final Properties properties;

    public Driver(DatabaseData databaseData){

        this.url= String.format("jdbc:postgresql://%s:%s/%s",
                databaseData.getHost(),
                databaseData.getPort(),
                databaseData.getDatabase());
        this.properties = new Properties();
        this.properties.setProperty("user", databaseData.getUserName());
        this.properties.setProperty("password", databaseData.getPassword());
    }

    public Connection getConnection(){
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(url,properties);
            return connection;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return null;
    }


}
