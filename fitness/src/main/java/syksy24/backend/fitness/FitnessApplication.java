package syksy24.backend.fitness;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Bean; //objektien luominen spring frameworkissa
import org.springframework.data.jpa.repository.config.EnableJpaRepositories; // tietokantaan tallentaminen, kommunikointi h2:n kanssa

import syksy24.backend.fitness.model.Exercise;
import syksy24.backend.fitness.model.ExerciseRepository; //työkalu exercisen luomiseen, muokkamiseen..

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

			exerciseRepository.save(new Exercise("Push-ups", "Classic upper body exercise", "Chest", "None", 5,
					"Beginner", Arrays.asList("Strength", "Upper Body"), 4.5)); // Add a rating value
			exerciseRepository.save(new Exercise("Squats", "Lower body compound exercise", "Legs", "None", 10,
					"Intermediate", Arrays.asList("Strength", "Lower Body"), 4.0)); // Add a rating value
			exerciseRepository.save(new Exercise("Plank", "Core strengthening exercise", "Core", "None", 3,
					"Beginner", Arrays.asList("Core", "Isometric"), 3.5)); // Add a rating value
			exerciseRepository.save(new Exercise("Jumping Jacks", "Full body cardio exercise", "Full Body", "None",
					5, "Beginner", Arrays.asList("Cardio", "Warm-up"), 4.2)); // Add a rating value
			exerciseRepository.save(new Exercise("Dumbbell Rows", "Back strengthening exercise", "Back",
					"Dumbbells", 8, "Intermediate", Arrays.asList("Strength", "Upper Body"), 4.7)); // Add a rating
																									// value

			log.info("Listing all exercises");
			for (Exercise exercise : exerciseRepository.findAll()) {
				log.info(exercise.toString());
			}
		};
	}
}
