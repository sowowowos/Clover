package loverduck.clover;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableScheduling
@SpringBootApplication
@EnableJpaAuditing
public class CloverApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloverApplication.class, args);
    }

}
