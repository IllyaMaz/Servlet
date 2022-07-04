package dll;

import config.Driver;
import model.DevelopersDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DevelopersRepository implements Repository<DevelopersDao> {

    private final Driver connector ;
    private static final String GET_ALL = "select * from developers ";
    private static final String SAVE = "insert into developers (id,first_name,age,gender,salary) values (?,?,?,?,?)";
    private static final String REMOVE = "delete from developers d where d.id=?";
    private static final String UPDATE = "update developers d " +
                                          "set first_name = ?," +
                                          "age = ?," +
                                          "gender = ?," +
                                          "salary = ?" +
                                          "where id = ?";
    private static final String  GET_BY_NAME = "select * from developers d where d.first_name = ?";


    public DevelopersRepository(Driver connection){
        this.connector=connection;
    }
    @Override
    public List<DevelopersDao> getAll()  {
        try(Connection connection = connector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL)){
            ResultSet set = preparedStatement.executeQuery();
            List<DevelopersDao> list = new ArrayList<>();
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
    public void save(DevelopersDao developersDao) {
        try(Connection connection = connector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SAVE)){
                preparedStatement.setLong(1,developersDao.getId());
                preparedStatement.setString(2,developersDao.getFirstName());
                preparedStatement.setInt(3,developersDao.getAge());
                preparedStatement.setString(4,developersDao.getGender());
                preparedStatement.setInt(5,developersDao.getSalary());
                preparedStatement.execute();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void remove(Long id) {
        try(Connection connection = connector.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(REMOVE)) {
            preparedStatement.setLong(1,id);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public DevelopersDao getByName(String name){
        try (Connection connection = connector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_NAME)){
            preparedStatement.setString(1,name);
            ResultSet resultSet = preparedStatement.executeQuery();
            DevelopersDao developersDao = new DevelopersDao();
            while (resultSet.next()){
                developersDao = mapToSet(resultSet);
            }
            return developersDao;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(DevelopersDao developersDao) {
        try(Connection connection = connector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {
            preparedStatement.setString(1,developersDao.getFirstName());
            preparedStatement.setInt(2,developersDao.getAge());
            preparedStatement.setString(3,developersDao.getGender());
            preparedStatement.setInt(4,developersDao.getSalary());
            preparedStatement.setLong(5,developersDao.getId());
            preparedStatement.executeUpdate();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public DevelopersDao mapToSet (ResultSet resultSet) throws SQLException {
        DevelopersDao developersDao = new DevelopersDao();

        developersDao.setId(resultSet.getLong("id"));
        developersDao.setAge(resultSet.getInt("age"));
        developersDao.setFirstName(resultSet.getString("first_name"));
        developersDao.setGender(resultSet.getString("gender"));
        developersDao.setSalary(resultSet.getInt("salary"));

        return developersDao;
    }


}
