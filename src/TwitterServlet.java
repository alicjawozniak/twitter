import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by alicja on 24.05.16.
 */
@WebServlet(name = "TwitterServlet")
public class TwitterServlet extends HttpServlet {
    public void init() throws ServletException
    {
        System.out.print("cjdck");
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        // Check if this is new comer on your web page.
        if (session.isNew()){
            session.setAttribute("isNew", "true");
        } else {
            session.setAttribute("isNew", "false");

        }
        // Set response content type
        response.setContentType("text/html");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();

        // Check if this is new comer on your web page.
        if (session.isNew()){
            session.setAttribute("isNew", "true");
        } else {
            session.setAttribute("isNew", "false");

        }
        // Set response content type
        response.setContentType("text/html");
    }
}
