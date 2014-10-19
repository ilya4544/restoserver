package servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import domain.Visit;
import org.hibernate.Session;
import util.HibernateUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

public class VoteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Long userId = Long.valueOf((String)req.getParameter("userId"));
            Long waiterId = Long.valueOf((String)req.getParameter("waiterId"));
            Integer rating = Integer.parseInt((String)req.getParameter("rating"));
            String comment = req.getParameter("review");
            Visit visit = new Visit(userId, waiterId, rating, comment, new Date());

            Session session = HibernateUtil.getSessionAnnotationFactory().getCurrentSession();
            session.beginTransaction();
            session.save(visit);
            session.getTransaction().commit();

            resp.setContentType("application/json");
            Gson gson = new GsonBuilder().create();
            PrintWriter out = resp.getWriter();
            out.append(gson.toJson(new State(true)));
            out.close();
        } catch (Exception e) {
            resp.setContentType("application/json");
            Gson gson = new GsonBuilder().create();
            PrintWriter out = resp.getWriter();
            e.printStackTrace(out);
            out.close();
        }
    }
}

