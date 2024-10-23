package syksy24.backend.fitness.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    @Query("SELECT DISTINCT e FROM Exercise e LEFT JOIN FETCH e.reviews")
    List<Exercise> findAllWithReviews();

    @Query("SELECT DISTINCT e FROM Exercise e LEFT JOIN FETCH e.reviews " +
            "WHERE LOWER(e.title) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(e.muscleGroup) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(e.difficultyLevel) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Exercise> searchExercises(@Param("keyword") String keyword);

    // find exercises by title
    List<Exercise> findByTitle(String title);
}