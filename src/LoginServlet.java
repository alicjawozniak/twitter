import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by alicja on 25.05.16.
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer badPasswordCount = 0;
        try {
            badPasswordCount = (Integer) session.getAttribute("badPasswordCount");
        } catch (Exception e) {
            e.printStackTrace();
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
        if (badPasswordCount >= 3) {
            System.out.print("badPasswordCount>=3");
        } else {
            String userName = request.getParameter("username");
            String password = request.getParameter("password");

            if (UserService.checkPassword(userName, password)) {
                System.out.print("dobre haslo");
                dispatcher = request.getRequestDispatcher("/twitter.jsp");
            } else {
                System.out.print("zle haslo");
                session.setAttribute("badPasswordCount", badPasswordCount + 1);
            }
        }
        dispatcher.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();

        // Check if this is new comer on your web page.
        if (session.isNew()) {
            session.setAttribute("isNew", "true");
        } else {
            session.setAttribute("isNew", "false");

        }
        // Set response content type
//        response.setContentType("text/html");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
        dispatcher.forward(request, response);
    }
}
