package pages;

import dll.DevelopersRepository;
import model.DevelopersDao;
import org.thymeleaf.context.Context;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/developers/*")
public class DevelopersPage extends ThymeleafControler{
    DevelopersRepository repository ;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");

        repository = new DevelopersRepository(getDriver());
        List<DevelopersDao> list = repository.getAll();
        Context context = new Context(
                req.getLocale(),
                Map.of( "developers",list));
        getEngine().process("developers", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (req.getRequestURI().contains("delete")){
            String id = req.getParameter("id");
            repository.remove(Long.valueOf(id));
        } else if (req.getRequestURI().contains("update")){
            String oldName = req.getParameter("oldName");
            String newName = req.getParameter("newName");
            String newAge = req.getParameter("newAge");
            String newGender = req.getParameter("newGender");
            String newSalary = req.getParameter("newSalary");
            String newProject = req.getParameter("newProject");
            DevelopersDao byName = repository.getByName(oldName);

            if (!newName.equals("")){
                byName.setFirstName(newName);
            }

            if (!newAge.equals("")){
                byName.setAge(Integer.parseInt(newAge));
            }

            if (!newGender.equals("")){
                byName.setGender(newGender);
            }

            if (!newSalary.equals("")){
                byName.setSalary(Integer.valueOf(newSalary));
            }

            if (!newProject.equals("")){
                byName.setNameProject(newProject);
            }

            repository.update(byName);
        } else {
            String id = req.getParameter("id");
            String name = req.getParameter("name");
            String age = req.getParameter("age");
            String gender = req.getParameter("gender");
            String salary = req.getParameter("salary");
            String nameProject = req.getParameter("nameProject");
            DevelopersDao developersDao = new DevelopersDao(Long.parseLong(id),
                    name,
                    Integer.parseInt(age),
                    gender,
                    Integer.parseInt(salary),nameProject);
            repository.save(developersDao);
        }


        resp.sendRedirect("/developers");
    }
}
