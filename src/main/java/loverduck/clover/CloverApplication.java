package loverduck.clover;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CloverApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloverApplication.class, args);
    }

}
