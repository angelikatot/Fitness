package syksy24.backend.fitness.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    @Query("SELECT DISTINCT e FROM Exercise e LEFT JOIN FETCH e.reviews")
    List<Exercise> findAllWithReviews();
}