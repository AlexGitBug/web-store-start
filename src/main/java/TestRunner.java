import util.HibernateUtil;

public class TestRunner {

    public static void main(String[] args) {

        try (var sessionFactory = HibernateUtil.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            session.beginTransaction();


//            var user = session.get(User.class, 1);
//            var product = session.get(Product.class, 1);
//            var product1 = session.get(Product.class, 2);
//
//            var order = Order.builder()
//                    .deliveryAdress(DeliveryAdress.builder()
//                            .city("Minsk")
//                            .street("Pobeda")
//                            .building(1)
//                            .build())
//                    .deliveryDate(LocalDate.of(2022, 2, 25))
//                    .paymentCondition(PaymentCondition.CASH)
//                    .build();
//            order.setUser(user);
//            session.save(order);
//
//            var shoppingCart = ShoppingCart.builder().build();
//            shoppingCart.addProduct(session, order, product, product1);


        }
    }
}

