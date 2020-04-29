package awe.ideeninitiative;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "awe.ideeninitiative")
public class IdeeninitiativeApplication {

	public static void main(String[] args) {
		SpringApplication.run(IdeeninitiativeApplication.class, args);
	}

}
