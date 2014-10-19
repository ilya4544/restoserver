package servlet;

import domain.Waiter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.hibernate.Query;
import org.hibernate.Session;
import util.HibernateUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class GetWaitersServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       Session session = HibernateUtil.getSessionAnnotationFactory().getCurrentSession();
        session.beginTransaction();

        List<Waiter> waiterList = (List<Waiter>) session.createCriteria(Waiter.class).list();
        resp.setContentType("application/json");
        Gson gson = new GsonBuilder().create();
        PrintWriter out = resp.getWriter();
        out.append("[");
        for (int i = 0; i < waiterList.size(); i++) {
            out.append(gson.toJson(waiterList.get(i)));
            if (i < waiterList.size() - 1)
                out.append(",");
        }
        out.append("]");
        session.getTransaction().commit();
        out.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}

