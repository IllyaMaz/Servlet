package pages;

import dll.ProjectRepository;
import model.ProjectsDao;
import org.thymeleaf.context.Context;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/projects/*")
public class ProjectsPage extends ThymeleafControler{
    ProjectRepository repository;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        repository = new ProjectRepository(getDriver());
        List<ProjectsDao> list = repository.getAll();
        Context context = new Context(
                req.getLocale(),
                Map.of("projects",list)
        );
        getEngine().process("projects", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (req.getRequestURI().contains("delete")){
            String id = req.getParameter("id");
            repository.remove(Long.valueOf(id));
        } else if (req.getRequestURI().contains("update")){
            String oldName = req.getParameter("oldName");
            String newName = req.getParameter("newName");
            String newDeadline = req.getParameter("newDeadline");
            String newCompany = req.getParameter("newCompany");
            String newCustomer =req.getParameter("newCustomer");
            ProjectsDao byName = repository.getByName(oldName);

            if (!newName.equals("")){
                byName.setNameProject(newName);
            }

            if (!newDeadline.equals("")){
                byName.setDeadline(newDeadline);
            }

            if (!newCompany.equals("")){
                byName.setNameCompany(newCompany);
            }

            if(!newCustomer.equals("")){
                byName.setNameCustomer(newCustomer);
            }


            repository.update(byName);
        }else {
            String id = req.getParameter("id");
            String name = req.getParameter("name");
            String deadline = req.getParameter("deadline");
            String nameCompany = req.getParameter("nameCompany");
            String nameCustomer = req.getParameter("nameCustomer");
            ProjectsDao projectsDao = new ProjectsDao(Integer.parseInt(id),name,deadline,nameCompany,nameCustomer);
            repository.save(projectsDao);
        }

        resp.sendRedirect("/projects");
    }
}
