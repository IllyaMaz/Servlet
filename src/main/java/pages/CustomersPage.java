package pages;

import dll.CustomersRepository;
import model.CustomersDao;
import org.thymeleaf.context.Context;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/customers/*")
public class CustomersPage extends ThymeleafControler{
    private CustomersRepository repository;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        repository = new CustomersRepository(getDriver());
        List<CustomersDao> list = repository.getAll();
        Context context = new Context(
                req.getLocale(),
                Map.of("customers", list)
        );
        getEngine().process("customers",context,resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(req.getRequestURI());
        if (req.getRequestURI().contains("delete")){
            String string = req.getParameter("id");
            Long id = Long.parseLong(string);
            repository.remove(id);
        } else if(req.getRequestURI().contains("update")){
            String oldName = req.getParameter("oldName");
            String newName = req.getParameter("newName");
            String newCompany = req.getParameter("newCompany");
            CustomersDao customersDao = repository.getByName(oldName);

            if (!newName.equals("")){
                customersDao.setName(newName);
            }

            if (!newCompany.equals("")){
                customersDao.setNameCompany(newCompany);
            }

            repository.update(customersDao);
        }else {
            String id = req.getParameter("id");
            String name = req.getParameter("name");
            String company = req.getParameter("company");
            CustomersDao customersDao = new CustomersDao();
            customersDao.setId(Long.parseLong(id));
            customersDao.setName(name);
            customersDao.setNameCompany(company);
            repository.save(customersDao);
         }

        resp.sendRedirect("/customers");
    }
}
