package pages;

import config.Driver;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.FileTemplateResolver;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.Connection;
import java.util.HashMap;

@WebServlet("/")
public class ThymeleafControler extends HttpServlet {
    private TemplateEngine engine;
    private Driver driver;
    private Connection connection;


    @Override
    public void init() throws ServletException {
        driver = new Driver("localhost", 5432,"Goit_task_1","postgres","VisaGold1234");
        connection = driver.getConnection();
        engine = new TemplateEngine();
        FileTemplateResolver resolver = new FileTemplateResolver();
        resolver.setPrefix(getServletContext().getRealPath("tamplates\\") + "/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode("HTML5");
        resolver.setOrder(engine.getTemplateResolvers().size());
        resolver.setCacheable(false);

        engine.addTemplateResolver(resolver);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        Context context = new Context(
                req.getLocale(),
                new HashMap<>()
        );
        engine.process("welcom", context, resp.getWriter());

    }

    public TemplateEngine getEngine(){
        return engine;
    }

    public Driver getDriver(){
        return driver;
    }
}
