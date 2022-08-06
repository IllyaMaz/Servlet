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
    private static final String GET_ALL = "select p.id, p.name_project, p.deadline, cu.first_name, co.name_company " +
            "from projects p " +
            "inner join customers cu on cu.id = p.id_customer " +
            "inner join companies co on co.id = p.id_company ";
    private static final String SAVE = "insert into projects(id,name_project,deadline,id_company,id_customer) values (?,?,?,?,?)";
    private static final String REMOVE = "delete from projects p where p.id=?";
    private static final String UPDATE = "update projects p set name_project=?, deadline=?, id_company =?, id_customer =? where p.id=?";
    private static final String GET_BY_NAME = "select p.id, p.name_project, p.deadline, co.name_company, cu.first_name " +
            "from projects p " +
            "inner join companies co on p.id_company=co.id " +
            "inner join customers cu on p.id_customer=cu.id " +
            "where p.name_project = ?";
    private static final String GET_COMPANY_ID = "select * from companies where name_company = ?";
    private static final String GET_CUSTOMER_ID = "select * from customers where first_name = ?";


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
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE);
             PreparedStatement getCompanyId = connection.prepareStatement(GET_COMPANY_ID);
             PreparedStatement getCustomerId = connection.prepareStatement(GET_CUSTOMER_ID)){

            getCompanyId.setString(1,projectsDao.getNameCompany());
            ResultSet rsCompany = getCompanyId.executeQuery();
            rsCompany.next();

            getCustomerId.setString(1,projectsDao.getNameCustomer());
            ResultSet rsCustomer = getCustomerId.executeQuery();
            rsCustomer.next();

            preparedStatement.setLong(1,projectsDao.getId());
            preparedStatement.setString(2,projectsDao.getNameProject());
            preparedStatement.setString(3,projectsDao.getDeadline());
            preparedStatement.setLong(4,rsCompany.getLong("id"));
            preparedStatement.setLong(5,rsCustomer.getLong("id"));

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
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE);
             PreparedStatement getCompanyId = connection.prepareStatement(GET_COMPANY_ID);
             PreparedStatement getCustomerId = connection.prepareStatement(GET_CUSTOMER_ID)){

            getCompanyId.setString(1,projectsDao.getNameCompany());
            ResultSet rsCompany = getCompanyId.executeQuery();
            rsCompany.next();

            getCustomerId.setString(1,projectsDao.getNameCustomer());
            ResultSet rsCustomer = getCustomerId.executeQuery();
            rsCustomer.next();

            preparedStatement.setString(1,projectsDao.getNameProject());
            preparedStatement.setString(2,projectsDao.getDeadline());
            preparedStatement.setLong(3,rsCompany.getLong("id"));
            preparedStatement.setLong(4,rsCustomer.getLong("id"));
            preparedStatement.setLong(5,projectsDao.getId());

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
        projectsDao.setNameCompany(resultSet.getString("name_company"));
        projectsDao.setNameCustomer(resultSet.getString("first_name"));

        return projectsDao;
    }
}
