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
            String old_name = req.getParameter("oldName");
            String new_name = req.getParameter("newName");
            String new_age = req.getParameter("newAge");
            String new_gender = req.getParameter("newGender");
            String new_salary = req.getParameter("newSalary");
            DevelopersDao byName = repository.getByName(old_name);

            if (!new_name.equals("")){
                byName.setFirstName(new_name);
            }

            if (!new_age.equals("")){
                byName.setAge(Integer.parseInt(new_age));
            }

            if (!new_gender.equals("")){
                byName.setGender(new_gender);
            }

            if (!new_salary.equals("")){
                byName.setSalary(Integer.valueOf(new_salary));
            }

            repository.update(byName);
        } else {
            String id = req.getParameter("id");
            String name = req.getParameter("name");
            String age = req.getParameter("age");
            String gender = req.getParameter("gender");
            String salary = req.getParameter("salary");
            DevelopersDao developersDao = new DevelopersDao(Long.parseLong(id),
                    name,
                    Integer.parseInt(age),
                    gender,
                    Integer.parseInt(salary));
            repository.save(developersDao);
        }


        resp.sendRedirect("/developers");
    }
}
