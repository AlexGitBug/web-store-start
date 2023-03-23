import com.dmdev.webStore.config.ApplicationConfiguration;
import com.dmdev.webStore.dao.repository.CatalogRepository;
import com.dmdev.webStore.util.DataImporter;
import com.dmdev.webStore.util.HibernateUtil;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApplicationRunner{

    public static void main(String[] args) {

        try (var context = new AnnotationConfigApplicationContext(ApplicationConfiguration.class)){
            var catalogRepository = context.getBean("catalogRepository", CatalogRepository.class);
            System.out.println(catalogRepository);
        }

    }
}
