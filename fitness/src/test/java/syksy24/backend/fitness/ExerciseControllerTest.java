package syksy24.backend.fitness;

import syksy24.backend.fitness.web.ExerciseController;

// Controller Tests:

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.ui.Model;
import syksy24.backend.fitness.model.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExerciseControllerTest {

    @Mock
    private ExerciseRepository exerciseRepository;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private Model model;

    @InjectMocks
    private ExerciseController exerciseController;

    private Exercise testExercise;
    private Review testReview;

    @BeforeEach
    void setUp() {
        testExercise = new Exercise("Push-ups", "Upper body exercise", "Chest", "None", 5, "Beginner");
        testExercise.setId(1L);

        testReview = new Review();
        testReview.setId(1L);
        testReview.setRating(5);
        testReview.setComment("Great exercise!");
        testReview.setExercise(testExercise);
    }

    @Test
    void showExercises_ShouldReturnExerciseListView() {
        // Arrange
        List<Exercise> exercises = Arrays.asList(testExercise);
        when(exerciseRepository.findAllWithReviews()).thenReturn(exercises);

        // Act
        String viewName = exerciseController.showExercises(model);

        // Assert
        verify(model).addAttribute("exercises", exercises);
        assertThat(viewName).isEqualTo("exerciseList");
    }

    @Test
    void showReviewsForExercise_WithValidId_ShouldReturnReviewListView() {
        // Arrange
        when(exerciseRepository.findById(1L)).thenReturn(Optional.of(testExercise));
        when(reviewRepository.findByExerciseId(1L)).thenReturn(Arrays.asList(testReview));

        // Act
        String viewName = exerciseController.showReviewsForExercise(1L, model);

        // Assert
        verify(model).addAttribute("exercise", testExercise);
        verify(model).addAttribute("reviews", Arrays.asList(testReview));
        assertThat(viewName).isEqualTo("reviewList");
    }

    @Test
    void showReviewsForExercise_WithInvalidId_ShouldThrowException() {
        // Arrange
        when(exerciseRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> exerciseController.showReviewsForExercise(999L, model));
    }

    @Test
    void saveExercise_WithValidExercise_ShouldRedirectToExerciseList() {
        // Arrange
        when(exerciseRepository.save(any(Exercise.class))).thenReturn(testExercise);

        // Act
        String viewName = exerciseController.saveExercise(testExercise, model);

        // Assert
        verify(exerciseRepository).save(testExercise);
        assertThat(viewName).isEqualTo("redirect:/exercises");
    }

    @Test
    void saveExercise_WithException_ShouldReturnToAddExerciseForm() {
        // Arrange
        when(exerciseRepository.save(any(Exercise.class))).thenThrow(new RuntimeException("Save failed"));

        // Act
        String viewName = exerciseController.saveExercise(testExercise, model);

        // Assert
        verify(model).addAttribute(eq("error"), anyString());
        assertThat(viewName).isEqualTo("addExercise");
    }

    @Test
    void searchExercises_ShouldReturnSearchResults() {
        // Arrange
        List<Exercise> searchResults = Arrays.asList(testExercise);
        when(exerciseRepository.searchExercises("push")).thenReturn(searchResults);

        // Act
        String viewName = exerciseController.searchExercises("push", model);

        // Assert
        verify(model).addAttribute("exercises", searchResults);
        verify(model).addAttribute("keyword", "push");
        assertThat(viewName).isEqualTo("searchResults");
    }
}