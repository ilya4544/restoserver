package servlet;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
        Session session = HibernateUtil.getSessionAnnotationFactory().getCurrentSession();
        try {
            session.beginTransaction();
            List<User> userList = session.createCriteria(User.class).add(Expression.eq("login", login)).list();
            if (userList.isEmpty()) {
                session.save(new User(login, hash, name, 0.0));
                session.getTransaction().commit();
            } else {
                throw new Exception("login already exists");
            }
            resp.setContentType("application/json");
            Gson gson = new GsonBuilder().create();
            PrintWriter out = resp.getWriter();
            out.append(new String(gson.toJson(new State(true)).getBytes(),"UTF-8"));
            resp.setCharacterEncoding("UTF-8");
            out.close();
        } catch (Exception e) {
            if (session.getTransaction().isActive())
                session.getTransaction().rollback();
            resp.setContentType("application/json");
            Gson gson = new GsonBuilder().create();
            PrintWriter out = resp.getWriter();
            out.append(new String(gson.toJson(new Error(e.getMessage())).getBytes(),"UTF-8"));
            resp.setCharacterEncoding("UTF-8");
            out.close();
        }
    }
}
