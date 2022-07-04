package model;

public class SkillsDao {
    private long id;
    private String nameSkill;
    private String levelSkill;

    public SkillsDao(long id, String nameSkill, String levelSkill){
        this.id=id;
        this.nameSkill=nameSkill;
        this.levelSkill=levelSkill;
    }

    public SkillsDao(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNameSkill() {
        return nameSkill;
    }

    public void setNameSkill(String nameSkill) {
        this.nameSkill = nameSkill;
    }

    public String getLevelSkill() {
        return levelSkill;
    }

    public void setLevelSkill(String levelSkill) {
        this.levelSkill = levelSkill;
    }
}
