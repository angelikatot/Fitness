package syksy24.backend.fitness.web;

import syksy24.backend.fitness.model.Exercise;
import syksy24.backend.fitness.model.ExerciseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exercises")
public class RestExerciseController {

    private static final Logger log = LoggerFactory.getLogger(RestExerciseController.class);

    @Autowired
    private ExerciseRepository exerciseRepository;

    // Get all exercises
    @GetMapping
    public List<Exercise> showExercises() {
        log.info("Showing all exercises");
        return exerciseRepository.findAll();
    }

    // Get a specific exercise by ID
    @GetMapping("/{id}")
    public ResponseEntity<Exercise> getExerciseById(@PathVariable Long id) {
        return exerciseRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Create a new exercise
    @PostMapping
    public ResponseEntity<Exercise> createExercise(@RequestBody Exercise exercise) {
        log.info("Saving exercise: " + exercise);
        Exercise savedExercise = exerciseRepository.save(exercise);
        return ResponseEntity.ok(savedExercise); // Return ResponseEntity wrapping the saved exercise
    }

    // Update an existing exercise
    @PutMapping("/{id}")
    public ResponseEntity<Exercise> updateExercise(@PathVariable Long id, @RequestBody Exercise exercise) {
        return exerciseRepository.findById(id)
                .map(existingExercise -> {
                    exercise.setId(existingExercise.getId());
                    return ResponseEntity.ok(exerciseRepository.save(exercise));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete an exercise by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExercise(@PathVariable Long id) {
        return exerciseRepository.findById(id)
                .map(exercise -> {
                    exerciseRepository.delete(exercise);
                    return ResponseEntity.noContent().<Void>build(); // Specify <Void> here
                })
                .orElse(ResponseEntity.notFound().<Void>build()); // Specify <Void> here
    }

}
