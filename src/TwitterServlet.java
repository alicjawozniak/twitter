import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Created by alicja on 24.05.16.
 */
@WebServlet(name = "TwitterServlet", urlPatterns = {"/twitter"})
public class TwitterServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        User user = (User) session.getAttribute("user");

        if (session.isNew() || user == null) {
            session.setAttribute("isNew", "true");
            response.sendRedirect("/login");
        } else {
            String text = request.getParameter("text");
            PostService.addPost(user.getName(), text);
            response.sendRedirect("/twitter");

        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();

        if (session.isNew() || session.getAttribute("user") == null) {
            session.setAttribute("isNew", "true");
            response.sendRedirect("/login");
        } else {
            List<Post> postList = PostService.query();
            session.setAttribute("postList", postList);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/twitter.jsp");
            dispatcher.forward(request, response);
        }
    }
}
