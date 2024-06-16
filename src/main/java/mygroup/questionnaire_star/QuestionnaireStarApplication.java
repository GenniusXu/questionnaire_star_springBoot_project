package mygroup.questionnaire_star;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class QuestionnaireStarApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuestionnaireStarApplication.class, args);
	}

}
