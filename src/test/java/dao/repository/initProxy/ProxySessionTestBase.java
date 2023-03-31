package dao.repository.initProxy;

//@RequiredArgsConstructor
//public abstract class ProxySessionTestBase {
//
//    protected static AnnotationConfigApplicationContext applicationContext;
//    protected static EntityManager entityManager;
//    protected static SessionFactory sessionFactory;
//
//    @BeforeAll
//    static void init() {
//        applicationContext = new AnnotationConfigApplicationContext(ApplicationConfigurationTest.class);
//        entityManager = applicationContext.getBean(EntityManager.class);
//        sessionFactory = applicationContext.getBean(SessionFactory.class);
//    }
//
//    @BeforeEach
//    void beginSession() {
//        deleteAll(entityManager);
//    }
//
////    @AfterEach
////    void commitSession() {
//////        entityManager.close();
////    }
//
//    @AfterAll
//    static void closeSessionFactory() {
//        sessionFactory.close();
//    }
//}
