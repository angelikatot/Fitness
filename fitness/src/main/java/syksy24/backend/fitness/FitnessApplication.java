package syksy24.backend.fitness;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Bean;
import syksy24.backend.fitness.model.Exercise;
import syksy24.backend.fitness.model.ExerciseRepository;

@SpringBootApplication
public class FitnessApplication {

	private static final Logger log = LoggerFactory.getLogger(FitnessApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(FitnessApplication.class, args);
	}

	@Bean
	public CommandLineRunner demoData(ExerciseRepository exerciseRepository) {
		return (args) -> {
			log.info("Saving some exercises");

			exerciseRepository
					.save(new Exercise("Push-ups", "Classic upper body exercise", "Chest", "None", 5, "Beginner"));
			exerciseRepository
					.save(new Exercise("Squats", "Lower body compound exercise", "Legs", "None", 10, "Intermediate"));
			exerciseRepository
					.save(new Exercise("Plank", "Core strengthening exercise", "Core", "None", 3, "Beginner"));
			exerciseRepository.save(
					new Exercise("Jumping Jacks", "Full body cardio exercise", "Full Body", "None", 5, "Beginner"));
			exerciseRepository.save(new Exercise("Dumbbell Rows", "Back strengthening exercise", "Back", "Dumbbells", 8,
					"Intermediate"));

			log.info("Listing all exercises");
			for (Exercise exercise : exerciseRepository.findAll()) {
				log.info(exercise.toString());
			}
		};
	}
}
