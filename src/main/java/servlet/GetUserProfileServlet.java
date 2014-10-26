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
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

/**
 * Created by Администратор on 18.10.2014.
 */
public class GetUserProfileServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Session session = HibernateUtil.getSessionAnnotationFactory().getCurrentSession();
        String token = req.getParameter("token");
        try {
            session.beginTransaction();
            List<Token> tokenList = session.createCriteria(Token.class).add(Expression.eq("token", token)).list();
            if (tokenList.isEmpty())
                throw new Exception("access denied");
            Token tkn = (Token) session.get(Token.class, tokenList.get(0).getId());
            Date currentDate = new Date();
            if (tkn.getDate().before(currentDate))
                throw new Exception("token is expired");
            currentDate.setDate(currentDate.getDate() + 5);
            tkn.setDate(currentDate);
            session.update(tkn);
            User user = (User) session.get(User.class, tkn.getUserId());
            session.getTransaction().commit();

            resp.setContentType("application/json");
            Gson gson = new GsonBuilder().create();
            PrintWriter out = resp.getWriter();
            out.append(gson.toJson(user));
            resp.setCharacterEncoding("UTF-8");
            out.close();
        } catch (Exception e) {
            if (session.getTransaction().isActive())
                session.getTransaction().rollback();
            resp.setContentType("application/json");
            Gson gson = new GsonBuilder().create();
            PrintWriter out = resp.getWriter();
            out.append(gson.toJson(new Error(e.getMessage())));
            resp.setCharacterEncoding("UTF-8");
            out.close();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

}
