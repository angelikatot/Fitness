package syksy24.backend.fitness;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import syksy24.backend.fitness.model.Exercise;
import syksy24.backend.fitness.model.ExerciseRepository;
import syksy24.backend.fitness.model.User;
import syksy24.backend.fitness.model.UserRepository;

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

	@Bean
	public CommandLineRunner initializeTestUsers(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			// create test user
			if (userRepository.findByUsername("testuser").isEmpty()) {
				User testUser = new User();
				testUser.setUsername("testuser");
				testUser.setPassword(passwordEncoder.encode("testpassword"));
				testUser.setEmail("testuser@example.com");
				testUser.setRole("USER");
				userRepository.save(testUser);
				log.info("Test user created successfully.");
			} else {
				log.info("Test user already exists.");
			}

			// Create admin user
			if (userRepository.findByUsername("admin").isEmpty()) {
				User adminUser = new User();
				adminUser.setUsername("admin");
				adminUser.setPassword(passwordEncoder.encode("adminpassword"));
				adminUser.setEmail("admin@example.com");
				adminUser.setRole("ADMIN");
				userRepository.save(adminUser);
				log.info("Admin user created successfully.");
			} else {
				log.info("Admin user already exists.");
			}
		};
	}
}
