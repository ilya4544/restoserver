package servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import domain.Token;
import domain.Visit;
import domain.Waiter;
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

/**
 * Created by Ilya on 26.02.2015.
 */
public class GetWaiterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        Gson gson = new GsonBuilder().create();
        PrintWriter out = resp.getWriter();
        String token;
        Long waiterId;

        try {
            token = req.getParameter("token");
            waiterId = Long.valueOf(req.getParameter("waiterId"));
        } catch (Exception e) {
            out.append(gson.toJson(new Error(e.getMessage())));
            out.close();
            return;
        }


        final Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            List<Token> tokenList = session.createCriteria(Token.class).add(Expression.eq("token", token)).list();
            if (!tokenList.isEmpty()) {
                Waiter waiter = (Waiter) session.get(Waiter.class, waiterId);
                session.getTransaction().commit();
                out.append(gson.toJson(waiter));
            } else {
                session.getTransaction().commit();
                out.append(gson.toJson(new Error("access denied")));
            }
        } catch (Exception e) {
            session.getTransaction().rollback();
            out.append(gson.toJson(new Error(e.getMessage())));
        } finally {
            out.close();
            session.close();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
