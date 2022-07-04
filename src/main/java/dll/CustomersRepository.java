package dll;

import config.Driver;
import model.CompaniesDao;
import model.CustomersDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomersRepository implements Repository<CustomersDao>{

    private final Driver connector ;
    private static final String FIND_BY_ID = "select * from customers c ";
    private static final String SAVE = "insert into customers (id,first_name) values (?,?)";
    private static final String REMOVE = "delete from customers c where c.id=?";
    private static final String UPDATE = "update customers c " +
            "set first_name = ?"+
            "where id = ?";
    private static final String GET_BY_NAME = "select * from customers c where c.first_name = ?";

    public CustomersRepository (Driver connection){
        this.connector=connection;
    }

    @Override
    public List<CustomersDao> getAll()  {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<CustomersDao> list = new ArrayList<>();
            while (resultSet.next()){
                list.add(mapToSet(resultSet));
            }
            return list;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void save(CustomersDao customersDao) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE)){
            preparedStatement.setLong(1,customersDao.getId());
            preparedStatement.setString(2,customersDao.getName());
            preparedStatement.execute();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void remove(Long id) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(REMOVE)){
            preparedStatement.setLong(1,id);
            preparedStatement.execute();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public CustomersDao getByName(String name){
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_NAME)){
            preparedStatement.setString(1,name);
            ResultSet resultSet = preparedStatement.executeQuery();
            CustomersDao customersDao = new CustomersDao();
            while (resultSet.next()){
                customersDao = mapToSet(resultSet);
            }
            return customersDao;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public void update(CustomersDao customersDao) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)){
            preparedStatement.setString(1,customersDao.getName());
            preparedStatement.setLong(2,customersDao.getId());
            preparedStatement.executeUpdate();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public CustomersDao mapToSet (ResultSet resultSet) throws SQLException {
        CustomersDao customersDao = new CustomersDao();
        customersDao.setId(resultSet.getInt("id"));
        customersDao.setName(resultSet.getString("first_name"));
        return customersDao;
    }
}
