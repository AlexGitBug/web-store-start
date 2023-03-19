import entity.Product;
import util.DataImporter;
import util.HibernateUtil;

public class TestRunner {

    public static void main(String[] args) {

        try (var sessionFactory = HibernateUtil.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            DataImporter.importData(sessionFactory);
            session.beginTransaction();


            session.getTransaction().commit();
        }

    }
}
