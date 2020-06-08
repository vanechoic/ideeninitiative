package awe.ideeninitiative;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Startpunkt der Anwendung.
 * @author Generiert durch Spring Initializr
 */
@SpringBootApplication
@EnableJpaRepositories(basePackages = "awe.ideeninitiative.model.repositories")
@ComponentScan(basePackages = "awe.ideeninitiative")
@Transactional
public class IdeeninitiativeApplication {

	static final Logger logger = LoggerFactory.getLogger(IdeeninitiativeApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(IdeeninitiativeApplication.class, args);
	}

}