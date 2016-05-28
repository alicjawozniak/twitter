import javax.servlet.RequestDispatcher;
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
@WebServlet(name = "TwitterServlet", urlPatterns = {"/twitter"})
public class TwitterServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        if (session.isNew() || session.getAttribute("user") == null) {
            session.setAttribute("isNew", "true");
            response.sendRedirect("/login");
        } else {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/twitter.jsp");
            dispatcher.forward(request, response);
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();

        if (session.isNew() || session.getAttribute("user") == null) {
            session.setAttribute("isNew", "true");
            response.sendRedirect("/login");
        } else {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/twitter.jsp");
            dispatcher.forward(request, response);
        }
    }
}
