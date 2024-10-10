package syksy24.backend.fitness.model;

import org.springframework.data.jpa.repository.JpaRepository;
import syksy24.backend.fitness.model.Exercise;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

}