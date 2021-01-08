package sk.westland.core.application;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import sk.westland.core.services.ServerService;

@SpringBootApplication(scanBasePackages = "sk.westland")
public class Application {

    @Bean
    public ServerService serverService() {
        return new ServerService();
    }
}
