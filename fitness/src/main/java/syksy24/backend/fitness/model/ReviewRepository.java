package syksy24.backend.fitness.model;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByExerciseId(Long exerciseId);
}
