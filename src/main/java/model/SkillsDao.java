package model;

public class SkillsDao {
    private long id;
    private String nameSkill;
    private String levelSkill;
    private String nameDeveloper;

    public SkillsDao(long id, String nameSkill, String levelSkill, String idDeveloper){
        this.id=id;
        this.nameSkill=nameSkill;
        this.levelSkill=levelSkill;
        this.nameDeveloper = idDeveloper;
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

    public String getNameDeveloper() {
        return nameDeveloper;
    }

    public void setNameDeveloper(String idDeveloper) {
        this.nameDeveloper = idDeveloper;
    }
}
