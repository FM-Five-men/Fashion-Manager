package fashionmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableScheduling
@SpringBootApplication
@EnableDiscoveryClient
public class FashionManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(FashionManagerApplication.class, args);
    }

}