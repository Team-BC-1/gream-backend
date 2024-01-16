package bc1.gream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GreamApplication {

    public static void main(String[] args) {
        SpringApplication.run(GreamApplication.class, args);
    }
}
