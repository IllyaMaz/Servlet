package dll;

import config.Driver;
import model.CustomersDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomersRepository implements Repository<CustomersDao>{

    private final Driver connector ;
    private static final String GET_ALL = "select c.id, c.first_name, co.name_company " +
            "from customers c " +
            "inner join companies co on c.id_company= co.id";
    private static final String SAVE = "insert into customers (id,first_name,id_company) values (?,?,?)";
    private static final String REMOVE = "delete from customers c where c.id=?";
    private static final String UPDATE = "update customers c " +
            "set first_name = ?, " +
            "id_company = ? "+
            "where id = ?";
    private static final String GET_BY_NAME = "select c.id, c.first_name, co.name_company " +
            "from customers c " +
            "inner join companies co on c.id_company = co.id " +
            "where c.first_name = ?";
    private static final String GET_COMPANY_BY_NAME = "select id from companies where name_company = ?";

    public CustomersRepository (Driver connection){
        this.connector=connection;
    }

    @Override
    public List<CustomersDao> getAll()  {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL)) {
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
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE);
             PreparedStatement getCompamyId = connection.prepareStatement(GET_COMPANY_BY_NAME)){

            getCompamyId.setString(1,customersDao.getNameCompany());
            ResultSet resultSet = getCompamyId.executeQuery();
            resultSet.next();

            preparedStatement.setLong(1,customersDao.getId());
            preparedStatement.setString(2,customersDao.getName());
            preparedStatement.setLong(3,resultSet.getLong("id"));
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
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE);
             PreparedStatement getCompanyId = connection.prepareStatement(GET_COMPANY_BY_NAME)){

            getCompanyId.setString(1,customersDao.getNameCompany());
            ResultSet resultSet = getCompanyId.executeQuery();
            resultSet.next();

            preparedStatement.setString(1,customersDao.getName());
            preparedStatement.setLong(2,resultSet.getLong("id"));
            preparedStatement.setLong(3,customersDao.getId());
            preparedStatement.executeUpdate();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public CustomersDao mapToSet (ResultSet resultSet) throws SQLException {
        CustomersDao customersDao = new CustomersDao();
        customersDao.setId(resultSet.getInt("id"));
        customersDao.setName(resultSet.getString("first_name"));
        customersDao.setNameCompany(resultSet.getString("name_company"));
        return customersDao;
    }
}
