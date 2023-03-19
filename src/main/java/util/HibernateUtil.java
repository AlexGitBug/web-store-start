package util;

import entity.Catalog;
import entity.Order;
import entity.Product;
import entity.ShoppingCart;
import entity.User;
import entity.converter.BirthdayConverter;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    public static SessionFactory buildSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());
        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(Product.class);
        configuration.addAnnotatedClass(Catalog.class);
        configuration.addAnnotatedClass(Order.class);
        configuration.addAnnotatedClass(ShoppingCart.class);
        configuration.addAttributeConverter(BirthdayConverter.class);
        configuration.configure();

        return configuration.buildSessionFactory();
    }
}
