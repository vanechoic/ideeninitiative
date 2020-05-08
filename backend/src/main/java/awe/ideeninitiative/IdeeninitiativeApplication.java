package awe.ideeninitiative;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "awe.ideeninitiative")
@ComponentScan(basePackages = "awe.ideeninitiative")
public class IdeeninitiativeApplication {

	static final Logger logger = LoggerFactory.getLogger(IdeeninitiativeApplication.class);

	public static void main(String[] args) {
		logger.error("   hi");
		SpringApplication.run(IdeeninitiativeApplication.class, args);
	}

}