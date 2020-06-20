package de.michelblank.brausteuerung.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("de.michelblank.brausteuerung.backend.repository")
public class BackendApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }
}
