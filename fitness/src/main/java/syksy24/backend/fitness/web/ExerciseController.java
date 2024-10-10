package syksy24.backend.fitness.web;

import syksy24.backend.fitness.model.Exercise;
import syksy24.backend.fitness.model.ExerciseRepository;
import syksy24.backend.fitness.model.Review;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class ExerciseController {

    private static final Logger log = LoggerFactory.getLogger(ExerciseController.class);

    @Autowired
    private ExerciseRepository exerciseRepository;

    @GetMapping("/index")
    public String index(Model model) {
        log.info("Index page for exercises");
        return "index";
    }

    // Show all exercises
    @GetMapping("/exercises")
    public String showExercises(Model model) {
        log.info("Showing all exercises");
        List<Exercise> exercises = exerciseRepository.findAll();
        model.addAttribute("exercises", exercises);
        return "exerciseList";
    }

    @GetMapping("/add")
    public String addExercise(Model model) {
        log.info("Creating a new exercise");
        Exercise exercise = new Exercise();
        exercise.setReviews(new ArrayList<>()); // Initialize the reviews list
        model.addAttribute("exercise", exercise);
        addCommonAttributes(model);
        return "addExercise";
    }

    @PostMapping("/save")
    public String saveExercise(@ModelAttribute("exercise") Exercise exercise,
            @RequestParam("tagsList") String tagsList) {
        log.info("Saving exercise: " + exercise);
        exercise.setTags(Arrays.asList(tagsList.split(","))); // Convert comma-separated string to List
        exerciseRepository.save(exercise);
        return "redirect:/exercises";
    }

    @GetMapping("/edit/{id}")
    public String editExercise(@PathVariable("id") Long id, Model model) {
        log.info("Editing exercise with ID: " + id);
        Exercise exercise = exerciseRepository.findById(id).orElse(null);
        if (exercise != null) {
            model.addAttribute("exercise", exercise);
            model.addAttribute("tagsList", String.join(",", exercise.getTags())); // Convert List to comma-separated
                                                                                  // string
            addCommonAttributes(model);
            return "editExercise";
        } else {
            log.warn("Exercise with ID: " + id + " not found");
            return "redirect:/exercises";
        }
    }

    @PostMapping("/update")
    public String updateExercise(@ModelAttribute("exercise") Exercise exercise,
            @RequestParam("tagsList") String tagsList) {
        log.info("Updating exercise with ID: " + exercise.getId());
        exercise.setTags(Arrays.asList(tagsList.split(","))); // Convert comma-separated string to List
        exerciseRepository.save(exercise);
        return "redirect:/exercises";
    }

    @GetMapping("/delete/{id}")
    public String deleteExercise(@PathVariable("id") Long id) {
        log.info("Deleting exercise with ID: " + id);
        exerciseRepository.deleteById(id);
        return "redirect:/exercises";
    }

    @GetMapping("/exercises/{id}/review")
    public String showReviewForm(@PathVariable Long id, Model model) {
        Exercise exercise = exerciseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Exercise not found"));

        model.addAttribute("exercise", exercise);
        model.addAttribute("review", new Review()); // Assuming you have a Review class
        return "reviewExercise"; // This should match the name of your HTML file
    }

    private void addCommonAttributes(Model model) {
        model.addAttribute("muscleGroups",
                Arrays.asList("Chest", "Back", "Legs", "Shoulders", "Arms", "Core", "Full Body"));
        model.addAttribute("difficultyLevels", Arrays.asList("Beginner", "Intermediate", "Advanced"));
        model.addAttribute("equipmentOptions",
                Arrays.asList("None", "Dumbbells", "Barbell", "Resistance Bands", "Kettlebell", "Machine"));
    }
}
