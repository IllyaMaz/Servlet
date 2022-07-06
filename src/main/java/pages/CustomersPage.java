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
            String old_name = req.getParameter("oldName");
            String new_name = req.getParameter("newName");
            CustomersDao customersDao = repository.getByName(old_name);
            customersDao.setName(new_name);
            repository.update(customersDao);
        }else {
            String id = req.getParameter("id");
            String name = req.getParameter("name");
            CustomersDao customersDao = new CustomersDao();
            customersDao.setId(Long.parseLong(id));
            customersDao.setName(name);
            repository.save(customersDao);
         }

        resp.sendRedirect("/customers");
    }
}
