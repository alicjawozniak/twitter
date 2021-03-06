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
@WebServlet(name = "TwitterServlet", urlPatterns = {"/twitter/*"})
public class TwitterServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        User user = (User) session.getAttribute("user");

        if (session.isNew() || user == null) {
            session.setAttribute("isNew", "true");
            response.sendRedirect("/login");
        } else {
            String text = request.getParameter("text");

            if (PostService.checkIfContainsNewLine(text)) {
                session.setAttribute("result", null);
                session.setAttribute("error", "Post nie może zawierać znaku nowej linii");
            } else {
                text = PostService.convertSpecialSigns(text);
                String userName = PostService.convertSpecialSigns(user.getName());
                PostService.addPost(userName, text);
            }
            response.sendRedirect("/twitter");

        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();

        //logout
        if ("/logout".equals(request.getPathInfo())) {
            session.invalidate();
            response.sendRedirect("/login");
        } else {
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

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.invalidate();
        response.sendRedirect("/login");

    }

}
