package servlet;

import domain.Image;
import domain.Token;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import util.HibernateUtil;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Date;
import java.util.List;

public class DownloadServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final Session session = HibernateUtil.getSessionFactory().openSession();
        String token = req.getParameter("token");

        try {
            session.beginTransaction();
            List<Token> tokenList = session.createCriteria(Token.class).add(Expression.eq("token", token)).list();
            if (!tokenList.isEmpty()) {
                Token tkn = (Token) session.get(Token.class, tokenList.get(0).getId());
                Date currentDate = new Date();
                if (!tkn.getDate().before(currentDate)) {
                    session.update(tkn);
                    Image userImage = (Image) session.get(Image.class, tkn.getUserId());
                    session.getTransaction().commit();

                    // Original image
                    InputStream in = new ByteArrayInputStream(userImage.getImage());
                    BufferedImage image = ImageIO.read(in);

                    resp.setContentType("image/png");
                    OutputStream outs = resp.getOutputStream();
                    ImageIO.write(image, "png", outs);
                    outs.close();
                } else {
                    session.getTransaction().commit();
                    resp.sendError(HttpServletResponse.SC_FORBIDDEN);
                }
            } else {
                session.getTransaction().commit();
                resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            }
        } catch (Exception e) {
            session.getTransaction().rollback();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
         } finally {
            session.close();
        }
    }
}
