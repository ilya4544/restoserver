package web;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import domain.Visit;
import domain.Waiter;
import org.hibernate.Session;
import servlet.User;
import util.HibernateUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by user on 19.10.2014.
 */
public class AjaxServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Session session = HibernateUtil.getSessionAnnotationFactory().getCurrentSession();
        session.beginTransaction();
        List<Visit> visits = session.createCriteria(Visit.class).list(); // Obtain all products.
        request.setAttribute("products", visits); // Store products in request scope.

        getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
    }

}
