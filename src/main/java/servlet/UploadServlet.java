package servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import domain.Image;
import domain.Token;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import util.HibernateUtil;

public class UploadServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if (!isMultipart) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        Gson gson = new GsonBuilder().create();
        PrintWriter out = resp.getWriter();
        final Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            upload.setSizeMax(1024 * 1024 * 10);

            // parse request
            List items = upload.parseRequest(request);
            FileItem id = (FileItem) items.get(0);
            String token = id.getString();

            // get uploaded file
            FileItem file = (FileItem) items.get(1);
            byte[] imgArr = file.get();

            session.beginTransaction();
            List<Token> tokenList = session.createCriteria(Token.class).add(Expression.eq("token", token)).list();
            if (!tokenList.isEmpty()) {
                Token tkn = (Token) session.get(Token.class, tokenList.get(0).getId());
                Date currentDate = new Date();
                if (!tkn.getDate().before(currentDate)) {
                    Image img = (Image) session.get(Image.class, tkn.getUserId());
                    img.setImage(imgArr);
                    session.update(img);
                    session.getTransaction().commit();
                    out.append(gson.toJson(new State(true)));
                } else {
                    session.getTransaction().commit();
                    out.append(gson.toJson(new Error("token is expired")));
                }
            } else {
                session.getTransaction().commit();
                out.append(gson.toJson(new Error("access denied")));
            }
        } catch (Exception e) {
            out.append(gson.toJson(new Error(e.getMessage())));
            session.getTransaction().rollback();
        } finally {
            session.close();
            out.close();
        }
    }
}
