package inpt.sud.springbackend;

import inpt.sud.springbackend.dao.CategoryRepository;
import inpt.sud.springbackend.dao.ProductRepository;
import inpt.sud.springbackend.data.Category;
import inpt.sud.springbackend.data.Product;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

import java.util.Random;

@SpringBootApplication
public class SpringbackendApplication implements CommandLineRunner {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;


    @Autowired
    private RepositoryRestConfiguration repositoryRestConfiguration;


    public static void main(String[] args) {
        SpringApplication.run(SpringbackendApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        repositoryRestConfiguration.exposeIdsFor(Product.class, Category.class);

        categoryRepository.save(new Category(null, "Computers", null, null));
        categoryRepository.save(new Category(null, "Printers", null, null));
        categoryRepository.save(new Category(null, "Smartphones", null, null));

        Random rnd = new Random();

        categoryRepository.findAll().forEach(category -> {
            for (int i = 0; i < 10; i++) {
                Product p = new Product();
                p.setName(RandomString.make(10));
                p.setCurrentPrice(100 + rnd.nextInt(10000));
                p.setAvailable(rnd.nextBoolean());
                p.setPromotion(rnd.nextBoolean());
                p.setSelected(rnd.nextBoolean());
                p.setCategory(category);
                p.setPhotoName("unknown.png");
                productRepository.save(p);

            }

        });

    }
}
