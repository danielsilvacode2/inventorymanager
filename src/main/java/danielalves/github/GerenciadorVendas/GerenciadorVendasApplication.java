package danielalves.github.GerenciadorVendas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class GerenciadorVendasApplication {

    public static void main(String[] args) {
        SpringApplication.run(GerenciadorVendasApplication.class, args);

    }

}
