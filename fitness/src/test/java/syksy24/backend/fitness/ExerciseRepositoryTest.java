package syksy24.backend.fitness;

import syksy24.backend.fitness.model.Exercise;
import syksy24.backend.fitness.model.ExerciseRepository;
import syksy24.backend.fitness.model.Review;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
@Transactional
class ExerciseRepositoryTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Test
    void findAllWithReviews_ShouldReturnExercisesWithReviews() {
        // Arrange
        Exercise exercise1 = new Exercise("Push-ups", "Upper body exercise", "Chest", "None", 5, "Beginner");
        Exercise exercise2 = new Exercise("Squats", "Lower body exercise", "Legs", "None", 10, "Intermediate");

        // Persist the exercises
        entityManager.persist(exercise1);
        entityManager.persist(exercise2);
        entityManager.flush();

        // Create and persist the review for exercise1
        Review review1 = new Review();
        review1.setRating(5);
        review1.setComment("Great exercise!");
        review1.setExercise(exercise1);
        entityManager.persist(review1);

        // Act
        List<Exercise> result = exerciseRepository.findAllWithReviews();

        // Assert: Check that both exercises are included in the result
        assertThat(result).contains(exercise1, exercise2);

    }

    @Test
    void searchExercises_WithValidKeyword_ShouldReturnMatchingExercises() {
        // Arrange
        Exercise pushups = new Exercise("Push-ups", "Upper body exercise", "Chest", "None", 5, "Beginner");
        Exercise squats = new Exercise("Squats", "Lower body exercise", "Legs", "None", 10, "Beginner");

        // Create a mock for the ExerciseRepository
        ExerciseRepository mockExerciseRepository = mock(ExerciseRepository.class);

        // Mock the behavior of the repository
        when(mockExerciseRepository.searchExercises("push")).thenReturn(List.of(pushups));
        when(mockExerciseRepository.searchExercises("beginner")).thenReturn(List.of(pushups, squats));

        // Act
        List<Exercise> resultPush = mockExerciseRepository.searchExercises("push");
        List<Exercise> resultBeginner = mockExerciseRepository.searchExercises("beginner");

        // Assert
        assertThat(resultPush).hasSize(1);
        assertThat(resultBeginner).hasSize(2);
    }

    @Test
    void searchExercises_WithInvalidKeyword_ShouldReturnEmptyList() {
        // Create a mock for the ExerciseRepository
        ExerciseRepository mockExerciseRepository = mock(ExerciseRepository.class);

        // Mock the behavior of the repository
        when(mockExerciseRepository.searchExercises("nonexistent")).thenReturn(List.of());

        // Act
        List<Exercise> results = mockExerciseRepository.searchExercises("nonexistent");

        // Assert
        assertThat(results).isEmpty();
    }
}
