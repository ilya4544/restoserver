import domain.User;
import domain.Visit;
import domain.Waiter;
import org.hibernate.Session;
import util.HibernateUtil;

import java.util.Date;

/**
 * Created by Администратор on 18.10.2014.
 */
public class App {

    public static void main(String[] args) {
       // Waiter waiter = new Waiter("Jennifer Lopez", "Restomania", 9);
        //Visit visit = new Visit(1L, 1L, 2, new Date());
        //Get Session
      //  Session session = HibernateUtil.getSessionAnnotationFactory().getCurrentSession();
        //start transaction
       // session.beginTransaction();
        //Save the Model object
       // session.save(waiter);
        //Commit transaction
     //   session.getTransaction().commit();
       // System.out.println(user.getId());

        //terminate session factory, otherwise program won't end
        //HibernateUtil.getSessionAnnotationFactory().close();
    }
}
