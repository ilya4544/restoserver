package servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import domain.Token;
import domain.User;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import util.HibernateUtil;
import util.SecurityUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

public class SignInServlet extends HttpServlet{
    private SecurityUtil securityUtil = new SecurityUtil();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String hash = req.getParameter("hash");
        Session session = HibernateUtil.getSessionAnnotationFactory().getCurrentSession();
        try {
            session.beginTransaction();
            Criteria criteria =  session.createCriteria(User.class);
            criteria.add(Expression.eq("login", login));
            List<User> userList = criteria.list();
            if (userList.size() == 0)
                throw new Exception("login not exists");
            User user = (User) session.get(User.class, userList.get(0).getId());
            resp.setContentType("application/json");
            Gson gson = new GsonBuilder().create();
            PrintWriter out = resp.getWriter();
            if (user.getHash().equals(hash)) {
                String token = securityUtil.nextSessionId();
                Date currentDate = new Date();
                currentDate.setDate(currentDate.getDate() + 5);
                List<Token> tokenList = session.createCriteria(Token.class).add(Expression.eq("userId",user.getId())).list();
                if (tokenList.isEmpty()) {
                    session.save(new Token(user.getId(), token, currentDate));
                } else {
                    tokenList.get(0).setToken(token);
                    tokenList.get(0).setDate(currentDate);
                    session.update(tokenList.get(0));
                }
                out.append(gson.toJson(new TokenKeeper(token)));
            } else {
                throw new Exception("access denied");
            }
            session.getTransaction().commit();
            out.close();
        } catch (Exception e) {
            if (session.getTransaction().isActive())
                session.getTransaction().rollback();
            resp.setContentType("application/json");
            Gson gson = new GsonBuilder().create();
            PrintWriter out = resp.getWriter();
            out.append(gson.toJson(new Error(e.getMessage())));
            out.close();
        }
    }
}
