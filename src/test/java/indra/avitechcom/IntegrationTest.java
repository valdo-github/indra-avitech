package indra.avitechcom;

import indra.avitechcom.model.User;
import indra.avitechcom.hibernate.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class IntegrationTest {

    @Test
    public void test() {
        User emp = new User();
        emp.setName("David");
        emp.setGuid(UUID.randomUUID());

        //Get Session
        SessionFactory sessionFactory = HibernateUtil.getSessionAnnotationFactory();
        Session session = sessionFactory.getCurrentSession();
        //start transaction
        session.beginTransaction();
        //Save the Model object
        session.save(emp);
        //Commit transaction
        session.getTransaction().commit();
        System.out.println("Employee ID="+emp.getId());

        //terminate session factory, otherwise program won't end
        sessionFactory.close();    }

}
