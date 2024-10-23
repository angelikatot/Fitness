package syksy24.backend.fitness.web;

import syksy24.backend.fitness.model.Exercise;
import syksy24.backend.fitness.model.ExerciseRepository;
import syksy24.backend.fitness.model.Review;
import syksy24.backend.fitness.model.ReviewRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/exercises")
public class ExerciseController {

    private static final Logger log = LoggerFactory.getLogger(ExerciseController.class);

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @GetMapping
    public String showExercises(Model model) {
        log.info("Showing all exercises");
        List<Exercise> exercises = exerciseRepository.findAllWithReviews();
        model.addAttribute("exercises", exercises);
        return "exerciseList";
    }

    @GetMapping("/{exerciseId}/reviews")
    public String showReviewsForExercise(@PathVariable("exerciseId") Long exerciseId, Model model) {
        log.info("Showing reviews for exercise with ID: " + exerciseId);
        Exercise exercise = exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new ResourceNotFoundException("Exercise not found"));
        model.addAttribute("exercise", exercise);
        List<Review> reviews = reviewRepository.findByExerciseId(exerciseId);
        model.addAttribute("reviews", reviews);
        return "reviewList";
    }

    @GetMapping("/{exerciseId}/add-review")
    public String showAddReviewForm(@PathVariable("exerciseId") Long exerciseId, Model model) {
        log.info("Showing add review form for exercise with ID: " + exerciseId);
        Exercise exercise = exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new ResourceNotFoundException("Exercise not found"));
        model.addAttribute("exercise", exercise);
        model.addAttribute("review", new Review());
        return "addReview";
    }

    @PostMapping("/{exerciseId}/add-review")
    public String addReview(@PathVariable("exerciseId") Long exerciseId, @ModelAttribute("review") Review review) {
        log.info("Adding review for exercise with ID: " + exerciseId);
        Exercise exercise = exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new ResourceNotFoundException("Exercise not found"));
        review.setExercise(exercise);
        reviewRepository.save(review);
        return "redirect:/exercises/{exerciseId}/reviews";
    }

    @GetMapping("/add")
    public String showAddExerciseForm(Model model) {
        log.info("Showing add exercise form");
        Exercise exercise = new Exercise();
        model.addAttribute("exercise", exercise);
        addCommonAttributes(model);
        return "addExercise";
    }

    @PostMapping("/save")
    public String saveExercise(@Valid @ModelAttribute("exercise") Exercise exercise,
            BindingResult bindingResult, Model model) {
        log.info("Attempting to save exercise: " + exercise);

        if (bindingResult.hasErrors()) {
            // in case of validation error
            addCommonAttributes(model);
            return "addExercise";
        }

        try {
            exerciseRepository.save(exercise);
            log.info("Exercise saved successfully");
            return "redirect:/exercises";
        } catch (Exception e) {
            log.error("Error saving exercise: ", e);
            model.addAttribute("error", "Failed to save exercise: " + e.getMessage());
            addCommonAttributes(model);
            return "addExercise";
        }
    }

    @GetMapping("/edit/{id}")
    public String editExercise(@PathVariable("id") Long id, Model model) {
        log.info("Editing exercise with ID: " + id);
        Exercise exercise = exerciseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Exercise not found"));
        model.addAttribute("exercise", exercise);
        addCommonAttributes(model);
        return "editExercise";
    }

    @PostMapping("/update")
    public String updateExercise(@Valid @ModelAttribute("exercise") Exercise exercise,
            BindingResult bindingResult) {
        log.info("Updating exercise with ID: " + exercise.getId());

        if (bindingResult.hasErrors()) {
            // in case of validation errors
            return "editExercise";
        }

        if (exerciseRepository.existsById(exercise.getId())) {
            exerciseRepository.save(exercise);
        } else {
            log.warn("Attempted to update non-existent exercise with ID: " + exercise.getId());
        }
        return "redirect:/exercises";
    }

    @PostMapping("/delete/{id}")
    public String deleteExercise(@PathVariable("id") Long id) {
        log.info("Deleting exercise with ID: " + id);
        exerciseRepository.deleteById(id);
        return "redirect:/exercises";
    }

    @GetMapping("/reviews/edit/{id}")
    public String editReview(@PathVariable("id") Long id, Model model) {
        log.info("Editing review with ID: " + id);
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found"));
        model.addAttribute("review", review);
        return "editReview";
    }

    @PostMapping("/reviews/update")
    public String updateReview(@ModelAttribute("review") Review review) {
        log.info("Updating review with ID: " + review.getId());
        reviewRepository.save(review);
        return "redirect:/exercises/" + review.getExercise().getId() + "/reviews";
    }

    @GetMapping("/reviews/delete/{id}")
    public String deleteReview(@PathVariable("id") Long id) {
        log.info("Deleting review with ID: " + id);
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found"));
        Long exerciseId = review.getExercise().getId();
        reviewRepository.deleteById(id);
        return "redirect:/exercises/" + exerciseId + "/reviews";
    }

    @GetMapping("/search")
    public String showSearchForm() {
        return "searchExercises";
    }

    @GetMapping("/searchResults")
    public String searchExercises(@RequestParam("keyword") String keyword, Model model) {
        log.info("Searching exercises with keyword: " + keyword);
        List<Exercise> searchResults = exerciseRepository.searchExercises(keyword);
        model.addAttribute("exercises", searchResults);
        model.addAttribute("keyword", keyword);
        return "searchResults";
    }

    private void addCommonAttributes(Model model) {
        model.addAttribute("muscleGroups",
                Arrays.asList("Chest", "Back", "Legs", "Shoulders", "Arms", "Core", "Full Body"));
        model.addAttribute("difficultyLevels", Arrays.asList("Beginner", "Intermediate", "Advanced"));
        model.addAttribute("equipmentOptions",
                Arrays.asList("None", "Dumbbells", "Barbell", "Resistance Bands", "Kettlebell", "Machine"));
    }
}
