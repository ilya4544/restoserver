package servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class GetWaitersServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ArrayList<Waiter> waiters = new ArrayList<Waiter>();
        waiters.add(new Waiter(1,"Ilya", 1));

        resp.setContentType("application/json");
        Gson gson = new GsonBuilder().create();
        PrintWriter out = resp.getWriter();
        out.append(gson.toJson(waiters));
        out.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}

