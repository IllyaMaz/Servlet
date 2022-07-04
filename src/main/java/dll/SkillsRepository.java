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
    private static final String GET_ALL = "select * from skills";
    private static final String SAVE = "insert into skills (id,name_skill,level_skill) values (?,?,?)";
    private static final String REMOVE = "delete from skills s where s.id=?";
    private static final String UPDATE = "update skills s set name_skill=?, level_skill=? where s.id=?";
    private static final String GET_BY_NAME_AND_LEVEL = "select * from skills s where s.name_skill = ? and s.level_skill = ?";

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
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE)){
            preparedStatement.setLong(1,skillsDao.getId());
            preparedStatement.setString(2,skillsDao.getNameSkill());
            preparedStatement.setString(3,skillsDao.getLevelSkill());
            preparedStatement.execute();
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

    public SkillsDao getByNameAndLevel(String name, String level){
        try (Connection connection = connector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_NAME_AND_LEVEL)){
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,level);
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
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)){
            preparedStatement.setString(1,skillsDao.getNameSkill());
            preparedStatement.setString(2,skillsDao.getLevelSkill());
            preparedStatement.setLong(3,skillsDao.getId());
            preparedStatement.executeUpdate();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private  SkillsDao mapToSet(ResultSet resultSet) throws SQLException {
        SkillsDao skillsDao = new SkillsDao();

        skillsDao.setId(resultSet.getLong("id"));
        skillsDao.setNameSkill(resultSet.getString("name_skill"));
        skillsDao.setLevelSkill(resultSet.getString("level_skill"));

        return skillsDao;
    }
}
