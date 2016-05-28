import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by alicja on 28.05.16.
 */
@WebServlet(name = "PasswordServlet", urlPatterns = "/password")
public class PasswordServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        if (session.isNew() || session.getAttribute("user") == null) {
            response.sendRedirect("/login");
        } else {
            User user = (User) session.getAttribute("user");
            String currentPassword = request.getParameter("currentPassword");
            String newPassword = request.getParameter("newPassword");
            user = UserService.checkPassword(user.getName(), currentPassword);
            if (user != null) {
                if (UserService.checkPasswordEntropy(newPassword)) {
                    UserService.changePassword(user, newPassword);
                } else {
                    session.setAttribute("error", "Za słabe nowe hasło");
                }
            } else {
                session.setAttribute("error", "Błędne hasło");
            }
            RequestDispatcher dispatcher = request.getRequestDispatcher("/password.jsp");
            dispatcher.forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        if (session.isNew() || session.getAttribute("user") == null) {
            response.sendRedirect("/login");
        } else {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/password.jsp");
            dispatcher.forward(request, response);
        }
    }
}
