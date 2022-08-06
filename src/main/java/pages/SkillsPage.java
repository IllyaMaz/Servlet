package pages;

import dll.SkillsRepository;
import model.SkillsDao;
import org.thymeleaf.context.Context;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/skills/*")
public class SkillsPage extends ThymeleafControler{
    SkillsRepository repository;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        repository = new SkillsRepository(getDriver());
        List<SkillsDao> list = repository.getAll();
        Context context = new Context(
                req.getLocale(),
                Map.of("skills",list)
        );
        getEngine().process("skills", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (req.getRequestURI().contains("delete")){
            String id = req.getParameter("id");
            repository.remove(Long.valueOf(id));
        } else if (req.getRequestURI().contains("update")){
            String oldName = req.getParameter("oldName");
            String oldLevel = req.getParameter("oldLevel");
            String oldNameDeveloper = req.getParameter("oldDeveloper");
            String newName = req.getParameter("newName");
            String newLevel = req.getParameter("newLevel");
            String newNameDeveloper = req.getParameter("newDeveloper");
            SkillsDao byNameAndLevel = repository.getByNameAndLevel(oldName, oldLevel,oldNameDeveloper);

            if (!newName.equals("")){
                byNameAndLevel.setNameSkill(newName);
            }

            if (!newLevel.equals("")){
                byNameAndLevel.setLevelSkill(newLevel);
            }

            if (!newNameDeveloper.equals("")){
                byNameAndLevel.setNameDeveloper(newNameDeveloper);
            }

            repository.update(byNameAndLevel);
        } else {
            String id = req.getParameter("id");
            String name = req.getParameter("name");
            String level = req.getParameter("level");
            String nameDeveloper = req.getParameter("nameDeveloper");
            SkillsDao skillsDao = new SkillsDao(Long.parseLong(id),name,level,nameDeveloper);
            repository.save(skillsDao);
        }
        resp.sendRedirect("/skills");
    }
}
