package servlet;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Hashtable;

public class QRCodeServlet extends HttpServlet {
    private BufferedImage getScaledImage(BufferedImage image, int width, int height) throws IOException {
        int imageWidth  = image.getWidth();
        int imageHeight = image.getHeight();

        double scaleX = (double)width/imageWidth;
        double scaleY = (double)height/imageHeight;
        AffineTransform scaleTransform = AffineTransform.getScaleInstance(scaleX, scaleY);
        AffineTransformOp bilinearScaleOp = new AffineTransformOp( scaleTransform, AffineTransformOp.TYPE_BILINEAR);

        return bilinearScaleOp.filter( image, new BufferedImage(width, height, image.getType()));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<EncodeHintType, ErrorCorrectionLevel>();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = qrCodeWriter.encode(id, BarcodeFormat.QR_CODE, 400, 400, hintMap);
            int matrixWidth = bitMatrix.getWidth();
            BufferedImage image = new BufferedImage(matrixWidth, matrixWidth, BufferedImage.TYPE_INT_RGB);
            image.createGraphics();
            Graphics2D graphics = (Graphics2D) image.getGraphics();

            graphics.setColor(Color.white);
            graphics.fillRect(0, 0, matrixWidth, matrixWidth);

            Color mainColor = new Color(93, 208, 238);
            graphics.setColor(mainColor);

            for (int i = 0; i < matrixWidth; i++) {
                for (int j = 0; j < matrixWidth; j++) {
                    if (bitMatrix.get(i, j)) {
                        graphics.fillRect(i, j, 1, 1);
                    }
                }
            }
            BufferedImage logo = ImageIO.read( QRCodeServlet.class.getResourceAsStream("logos.png"));
            graphics.drawImage( logo,
                    image.getWidth()/2 - logo.getWidth()/2,
                    image.getHeight()/2 - logo.getHeight()/2,
                    image.getWidth()/2 + logo.getWidth()/2,
                    image.getHeight()/2 + logo.getHeight()/2,
                    0, 0, logo.getWidth(), logo.getHeight(), null);

            resp.setContentType("image/png");
            OutputStream out = resp.getOutputStream();
            ImageIO.write(image, "png", out);
            out.close();
        } catch (WriterException e) {
            e.printStackTrace();
        }

    }
}
