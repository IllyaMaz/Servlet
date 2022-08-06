package dll;


import config.Driver;
import model.SkillsDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SkillsRepository implements Repository<SkillsDao>{

    private final Driver connector;
    private static final String GET_ALL = "select s.id as id, s.name_skill as name, s.level_skill as level, d.first_name as nameDeveloper" +
            " from skills s" +
            " inner join developers d on s.id_developer=d.id";
    private static final String SAVE = "insert into skills (id,name_skill,level_skill,id_developer) values (?,?,?,?)";
    private static final String REMOVE = "delete from skills s where s.id=?";
    private static final String UPDATE = "update skills s set name_skill=?, level_skill=?, id_developer = ? where s.id=?";
    private static final String GET_BY_NAME_AND_LEVEL = "select  s.id as id, s.name_skill as name, s.level_skill as level, d.first_name as nameDeveloper " +
            "from skills s " +
            "inner join developers d on s.id_developer=d.id " +
            "where s.name_skill = ? and s.level_skill = ? and id_developer = ?";
    private static final String GET_BY_NAME_DEVELOPER = "select id from developers where first_name = ?";

    public SkillsRepository(Driver connection){
        this.connector=connection;
    }

    @Override
    public List<SkillsDao> getAll() {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL)){
            ResultSet resultSet = preparedStatement.executeQuery();
            List<SkillsDao> list = new ArrayList<>();
            while (resultSet.next()){
                list.add(mapToSet(resultSet));
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void save(SkillsDao skillsDao) {
        try (Connection connection = connector.getConnection();
             PreparedStatement save = connection.prepareStatement(SAVE);
             PreparedStatement getIdDeveloper = connection.prepareStatement(GET_BY_NAME_DEVELOPER)){
            getIdDeveloper.setString(1,skillsDao.getNameDeveloper());
            ResultSet resultSet = getIdDeveloper.executeQuery();
            resultSet.next();
            save.setLong(1,skillsDao.getId());
            save.setString(2,skillsDao.getNameSkill());
            save.setString(3,skillsDao.getLevelSkill());
            save.setLong(4,resultSet.getLong("id"));
            save.execute();
        } catch (Exception e) {
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

    public SkillsDao getByNameAndLevel(String name, String level, String nameDeveloper){
        try (Connection connection = connector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_NAME_AND_LEVEL);
            PreparedStatement getIdDeveloper = connection.prepareStatement(GET_BY_NAME_DEVELOPER)){
            getIdDeveloper.setString(1,nameDeveloper);
            ResultSet getId = getIdDeveloper.executeQuery();
            getId.next();
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,level);
            preparedStatement.setLong(3,getId.getLong("id"));
            ResultSet resultSet = preparedStatement.executeQuery();
            SkillsDao skillsDao = new SkillsDao();
            while (resultSet.next()){
                skillsDao = mapToSet(resultSet);
            }
            return skillsDao;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(SkillsDao skillsDao) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE);
             PreparedStatement getIdDeveloper = connection.prepareStatement(GET_BY_NAME_DEVELOPER)){
            getIdDeveloper.setString(1,skillsDao.getNameDeveloper());
            ResultSet resultSet = getIdDeveloper.executeQuery();
            resultSet.next();
            preparedStatement.setString(1,skillsDao.getNameSkill());
            preparedStatement.setString(2,skillsDao.getLevelSkill());
            preparedStatement.setLong(3,resultSet.getLong("id"));
            preparedStatement.setLong(4,skillsDao.getId());
            preparedStatement.executeUpdate();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private  SkillsDao mapToSet(ResultSet resultSet) throws SQLException {
        SkillsDao skillsDao = new SkillsDao();

        skillsDao.setId(resultSet.getLong("id"));
        skillsDao.setNameSkill(resultSet.getString("name"));
        skillsDao.setLevelSkill(resultSet.getString("level"));
        skillsDao.setNameDeveloper(resultSet.getString("nameDeveloper"));

        return skillsDao;
    }
}
