package pages;

import dll.CompaniesRepository;
import model.CompaniesDao;
import org.thymeleaf.context.Context;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/companies/*")
public class CompaniesPage extends ThymeleafControler {
    CompaniesRepository repository ;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");

        repository = new CompaniesRepository(getDriver());
        List<CompaniesDao> list = repository.getAll();
        Context context = new Context(
                req.getLocale(),
                Map.of("query",list)
        );
        try {
            getEngine().process("companies", context, resp.getWriter());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getRequestURI().contains("delete")){
        String string = req.getParameter("id");
        Long id = Long.parseLong(string);
        repository.remove(id);
        } else if (req.getRequestURI().contains("update")){
        String oldName = req.getParameter("old_name");
        String newName = req.getParameter("new_name");
        CompaniesDao companiesDao = repository.getByName(oldName);
        companiesDao.setNameCompany(newName);
        repository.update(companiesDao);
        }else {
        Long id = Long.valueOf(req.getParameter("id"));
        String nameCompanies = req.getParameter("name");

        CompaniesDao companiesDao = new CompaniesDao();
        companiesDao.setId(id);
        companiesDao.setNameCompany(nameCompanies);

        repository.save(companiesDao);
        }

        resp.sendRedirect("/companies");
    }
}
