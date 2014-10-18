package servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.hibernate.Session;
import util.HibernateUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Администратор on 18.10.2014.
 */
public class GetUserProfileServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.valueOf(req.getParameter("id"));
        Session session = HibernateUtil.getSessionAnnotationFactory().getCurrentSession();
        User user = (User) session.get(User.class, id);
        HibernateUtil.getSessionAnnotationFactory().close();

        resp.setContentType("application/json");
        Gson gson = new GsonBuilder().create();
        PrintWriter out = resp.getWriter();
        out.append(gson.toJson(user));
        out.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

}
