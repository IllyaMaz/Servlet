package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Driver {
    private final String url;
    private final Properties properties;

    public Driver(String host, int port, String database, String userName, String password){
        this.url = String.format("jdbc:postgresql://%s:%d/%s",host,port,database);
        this.properties = new Properties();
        properties.setProperty("user",userName);
        properties.setProperty("password",password);
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
