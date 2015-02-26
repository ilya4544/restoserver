package servlet;

import domain.Token;
import domain.User;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import util.HibernateUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transaction;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

public class GetUserProfileServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final Session session = HibernateUtil.getSessionFactory().openSession();
        String token = req.getParameter("token");
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        Gson gson = new GsonBuilder().create();
        PrintWriter out = resp.getWriter();

        try {
            session.beginTransaction();
            List<Token> tokenList = session.createCriteria(Token.class).add(Expression.eq("token", token)).list();
            if (!tokenList.isEmpty()) {
                Token tkn = (Token) session.get(Token.class, tokenList.get(0).getId());
                Date currentDate = new Date();
                if (!tkn.getDate().before(currentDate)) {
                    currentDate.setDate(currentDate.getDate() + 5);
                    tkn.setDate(currentDate);
                    session.update(tkn);
                    User user = (User) session.get(User.class, tkn.getUserId());
                    session.getTransaction().commit();
                    out.append(gson.toJson(user));
                } else {
                    session.getTransaction().commit();
                    out.append(gson.toJson(new Error("token is expired")));
                }
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
