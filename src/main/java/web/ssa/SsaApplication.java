package web.ssa;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import web.ssa.entity.categories.Categories;
import web.ssa.enumf.CategoryType;
import web.ssa.repository.categories.CategoryRepository;

@SpringBootApplication
public class SsaApplication {

    public static void main(String[] args) {

        SpringApplication.run(SsaApplication.class, args);

    }

    @Bean
    public CommandLineRunner initCategories(CategoryRepository catRepository) {
        return args -> {
            for (CategoryType type : CategoryType.values()) {
                if (catRepository.findByCode(type).isEmpty()) {
                    catRepository.save(Categories.builder()
                            .code(type)
                            .name(type.getDisplayName())
                            .build());
                }
            }
        };
    }

}
