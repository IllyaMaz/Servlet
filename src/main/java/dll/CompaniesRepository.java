package dll;

import config.Driver;
import model.CompaniesDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CompaniesRepository implements Repository<CompaniesDao>{

    private final Driver connector ;
    private static final String GET_ALL = "select * from companies ";
    private static final String SAVE = "insert into companies (id,name_company) values (?,?)";
    private static final String REMOVE = "delete from companies c where c.id=?";
    private static final String UPDATE = "update companies c " +
            "set name_company = ?"+
            "where id = ?";
    private static final String GET_BY_NAME = "select * from companies c where c.name_company = ?";

    public CompaniesRepository(Driver connection) {
        connector = connection;
    }


    @Override
    public List<CompaniesDao> getAll() {
        try(Connection connection = connector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL);){
            ResultSet set = preparedStatement.executeQuery();
            System.out.println(set.getFetchSize());
            List<CompaniesDao> list = new ArrayList<>();
            while (set.next()){
                list.add(mapToSet(set));
            }
            return list;
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void save(CompaniesDao companiesDao) {
        try(Connection connection = connector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SAVE)){
            preparedStatement.setLong(1,companiesDao.getId());
            preparedStatement.setString(2,companiesDao.getNameCompany());
            preparedStatement.execute();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void remove(Long id) {
        try(Connection connection = connector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(REMOVE)) {
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public CompaniesDao getByName(String name){
        try (Connection connection = connector.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_NAME)){
            preparedStatement.setString(1,name);
            ResultSet resultSet = preparedStatement.executeQuery();
            CompaniesDao companiesDao = new CompaniesDao();
            while (resultSet.next()){
                companiesDao = mapToSet(resultSet);
            }
            return companiesDao;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

   @Override
    public void update(CompaniesDao companiesDao) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)){
            preparedStatement.setString(1,companiesDao.getNameCompany());
            preparedStatement.setLong(2,companiesDao.getId());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public CompaniesDao mapToSet (ResultSet resultSet) throws SQLException {
        CompaniesDao companiesDao = new CompaniesDao();
            companiesDao.setId(resultSet.getInt("id"));
            companiesDao.setNameCompany(resultSet.getString("name_company"));

        return companiesDao;
    }
}
