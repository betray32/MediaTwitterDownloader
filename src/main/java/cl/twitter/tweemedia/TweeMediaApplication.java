package cl.twitter.tweemedia;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Twitter media Manager - TweeMedia"))
public class TweeMediaApplication {
    public static void main(String[] args) {
        SpringApplication.run(TweeMediaApplication.class, args);
    }
}
