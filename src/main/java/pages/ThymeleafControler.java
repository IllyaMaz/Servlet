package pages;

import com.google.gson.Gson;
import config.DatabaseData;
import config.Driver;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.FileTemplateResolver;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;

@WebServlet("/")
public class ThymeleafControler extends HttpServlet {
    private TemplateEngine engine;
    private Driver driver;
    private File file ;


    @Override
    public void init() throws ServletException {
        this.file = new File(getServletContext().getRealPath("properties.json"));
        this.driver = new Driver(getData());
        this.engine = new TemplateEngine();
        FileTemplateResolver resolver = new FileTemplateResolver();
        resolver.setPrefix(getServletContext().getRealPath("tamplates") + "/");
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

    public DatabaseData getData() {
        Gson gson = new Gson();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))){
            String result = "";
            String line = reader.readLine();
            while (line != null){
                result += line;
                line = reader.readLine();
            }

            return gson.fromJson(result,DatabaseData.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
