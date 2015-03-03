package servlet;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import domain.Image;
import domain.User;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import util.HibernateUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class SignUpServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String hash = req.getParameter("hash");
        String name = req.getParameter("name");

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        Gson gson = new GsonBuilder().create();
        PrintWriter out = resp.getWriter();

        final Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            List<User> userList = session.createCriteria(User.class).add(Expression.eq("login", login)).list();
            if (userList.isEmpty()) {
                session.save(new User(login, hash, name, 0.0));
                session.save(new Image());
                session.getTransaction().commit();
                out.append(gson.toJson(new State(true)));
            } else {
                session.getTransaction().commit();
                out.append(gson.toJson(new Error("login already exists")));
            }
        } catch (Exception e) {
            session.getTransaction().rollback();
            out.append(gson.toJson(new Error(e.getMessage())));
        } finally {
            out.close();
            session.close();
        }
    }
}
