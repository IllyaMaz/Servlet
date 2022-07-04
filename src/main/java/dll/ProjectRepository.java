package dll;

import config.Driver;
import model.ProjectsDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProjectRepository implements Repository<ProjectsDao>{

    private final Driver connector;
    private static final String GET_ALL = "select * from projects";
    private static final String SAVE = "insert into projects(id,name_project,deadline,cost) values (?,?,?,?)";
    private static final String REMOVE = "delete from projects p where p.id=?";
    private static final String UPDATE = "update projects p set name_project=?, deadline=?, cost=? where p.id=?";
    private static final String GET_BY_NAME = "select * from projects p where p.name_project = ?";


    public ProjectRepository(Driver connection){
        this.connector=connection;
    }


    @Override
    public List<ProjectsDao> getAll() {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL)){
            ResultSet resultSet = preparedStatement.executeQuery();
            List<ProjectsDao> list = new ArrayList<>();
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
    public void save(ProjectsDao projectsDao) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE)){
            preparedStatement.setLong(1,projectsDao.getId());
            preparedStatement.setString(2,projectsDao.getNameProject());
            preparedStatement.setString(3,projectsDao.getDeadline());
            preparedStatement.setInt(4,projectsDao.getCost());
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

    public ProjectsDao getByName(String name){
        try (Connection connection = connector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_NAME)){
            preparedStatement.setString(1,name);
            ResultSet resultSet = preparedStatement.executeQuery();
            ProjectsDao projectsDao = new ProjectsDao();
            while (resultSet.next()){
                projectsDao = mapToSet(resultSet);
            }
            return projectsDao;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(ProjectsDao projectsDao) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)){
            preparedStatement.setString(1,projectsDao.getNameProject());
            preparedStatement.setString(2,projectsDao.getDeadline());
            preparedStatement.setInt(3,projectsDao.getCost());
            preparedStatement.setLong(4,projectsDao.getId());
            preparedStatement.executeUpdate();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private ProjectsDao mapToSet (ResultSet resultSet) throws SQLException {
        ProjectsDao projectsDao = new ProjectsDao();

        projectsDao.setId(resultSet.getInt("id"));
        projectsDao.setNameProject(resultSet.getString("name_project"));
        projectsDao.setDeadline(resultSet.getString("deadline"));
        projectsDao.setCost(resultSet.getInt("cost"));

        return projectsDao;
    }
}
