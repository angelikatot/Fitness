package syksy24.backend.fitness;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import syksy24.backend.fitness.model.Exercise;
import syksy24.backend.fitness.model.ExerciseRepository;

import java.util.Arrays;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "syksy24.backend.fitness.model")
public class FitnessApplication {

	private static final Logger log = LoggerFactory.getLogger(FitnessApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(FitnessApplication.class, args);
	}

	@Bean
	public CommandLineRunner demoData(ExerciseRepository exerciseRepository) {
		return (args) -> {
			log.info("Saving some exercises");

			exerciseRepository.save(new Exercise("Push-ups", "Classic upper body exercise", 4.5, "Chest", "None", 5,
					"Beginner", Arrays.asList("Strength", "Upper Body")));
			exerciseRepository.save(new Exercise("Squats", "Lower body compound exercise", 4.8, "Legs", "None", 10,
					"Intermediate", Arrays.asList("Strength", "Lower Body")));
			exerciseRepository.save(new Exercise("Plank", "Core strengthening exercise", 4.2, "Core", "None", 3,
					"Beginner", Arrays.asList("Core", "Isometric")));
			exerciseRepository.save(new Exercise("Jumping Jacks", "Full body cardio exercise", 4.0, "Full Body", "None",
					5, "Beginner", Arrays.asList("Cardio", "Warm-up")));
			exerciseRepository.save(new Exercise("Dumbbell Rows", "Back strengthening exercise", 4.6, "Back",
					"Dumbbells", 8, "Intermediate", Arrays.asList("Strength", "Upper Body")));

			log.info("Listing all exercises");
			for (Exercise exercise : exerciseRepository.findAll()) {
				log.info(exercise.toString());
			}
		};
	}
}