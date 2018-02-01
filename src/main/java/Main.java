import org.hibernate.HibernateException;
import org.hibernate.Metamodel;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import vitaliy94.attendanceControl.model.Lecturers;
import vitaliy94.attendanceControl.util.HibernateUtil;

import javax.persistence.metamodel.EntityType;

import java.util.List;
import java.util.Map;

/**
 * Created by vitaliy on 01.02.2018.
 */
public class Main {


    public static void main(final String[] args) throws Exception {
        final Session session = HibernateUtil.getSession();
        try {
//            System.out.println("querying all the managed entities...");
//            final Metamodel metamodel = session.getSessionFactory().getMetamodel();
//            for (EntityType<?> entityType : metamodel.getEntities()) {
//                final String entityName = entityType.getName();
//                final Query query = session.createQuery("from " + entityName);
//                System.out.println("executing: " + query.getQueryString());
//                for (Object o : query.list()) {
//                    System.out.println("  " + o);
//                }
//            }
            final Query query = session.createQuery("from Lecturers ");
            List<Lecturers> lect = query.list();
            for(Lecturers l : lect)
            {
                System.out.println(l.getName());
            }
        } finally {
            session.close();
            System.out.println("close");
        }
    }
}