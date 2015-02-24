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
import java.util.Date;
import java.util.List;

public class VoteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        Gson gson = new GsonBuilder().create();
        PrintWriter out = resp.getWriter();

        final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        try {
            String token = req.getParameter("token");
            Long waiterId = Long.valueOf(req.getParameter("waiterId"));
            Integer rating = Integer.parseInt(req.getParameter("rating"));
            String comment = req.getParameter("review");

            session.beginTransaction();
            List<Token> tokenList = session.createCriteria(Token.class).add(Expression.eq("token", token)).list();
            if (!tokenList.isEmpty()) {
                Visit visit = new Visit(tokenList.get(0).getUserId(), waiterId, rating, comment, new Date());
                session.save(visit);
                Waiter waiter = (Waiter) session.get(Waiter.class, waiterId);
                if (waiter.getCountRating().equals(0)) {
                    waiter.setRating(rating);
                } else {
                    waiter.setRating((waiter.getRating() * waiter.getCountRating() + rating) / (waiter.getCountRating() + 1));
                }

                waiter.setCountRating(waiter.getCountRating() + 1);
                session.update(waiter);
                session.getTransaction().commit();
                out.append(new String(gson.toJson(new State(true)).getBytes(), "UTF-8"));
            } else {
                session.getTransaction().commit();
                out.append(gson.toJson(new Error("access denied")));
            }
        } catch (Exception e) {
            session.getTransaction().rollback();
            out.append(gson.toJson(new Error(e.getMessage())));
        } finally {
            out.close();
        }
    }
}

