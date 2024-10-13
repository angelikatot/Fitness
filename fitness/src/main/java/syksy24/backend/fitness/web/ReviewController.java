
/*
 * 
 * package syksy24.backend.fitness.web;
 * 
 * import syksy24.backend.fitness.model.Review;
 * import syksy24.backend.fitness.model.ReviewRepository;
 * import syksy24.backend.fitness.model.Exercise;
 * import syksy24.backend.fitness.model.ExerciseRepository;
 * import org.slf4j.Logger;
 * import org.slf4j.LoggerFactory;
 * import org.springframework.beans.factory.annotation.Autowired;
 * import org.springframework.stereotype.Controller;
 * import org.springframework.ui.Model;
 * import org.springframework.web.bind.annotation.*;
 * 
 * import java.util.List;
 * 
 * @Controller
 * 
 * @RequestMapping("/reviews")
 * public class ReviewController {
 * 
 * private static final Logger log =
 * LoggerFactory.getLogger(ReviewController.class);
 * 
 * @Autowired
 * private ReviewRepository reviewRepository;
 * 
 * @Autowired
 * private ExerciseRepository exerciseRepository;
 * 
 * // Display the form to add a review for a specific exercise
 * 
 * @GetMapping("/exercise/{exerciseId}/add")
 * public String showAddReviewForm(@PathVariable("exerciseId") Long exerciseId,
 * Model model) {
 * log.info("Showing add review form for exercise with ID: " + exerciseId);
 * Exercise exercise = exerciseRepository.findById(exerciseId).orElse(null);
 * if (exercise != null) {
 * model.addAttribute("exercise", exercise);
 * model.addAttribute("review", new Review()); // Initialize a new Review object
 * return "addReview"; // Return the view for adding a review
 * }
 * log.warn("Exercise with ID: " + exerciseId + " not found");
 * return "redirect:/exercises"; // Redirect if not found
 * }
 * 
 * // Handle the submission of the review form
 * 
 * @PostMapping("/exercise/{exerciseId}/add")
 * public String addReview(@PathVariable("exerciseId") Long
 * exerciseId, @ModelAttribute("review") Review review) {
 * log.info("Adding review for exercise with ID: " + exerciseId);
 * Exercise exercise = exerciseRepository.findById(exerciseId).orElse(null);
 * if (exercise != null) {
 * review.setExercise(exercise);
 * reviewRepository.save(review);
 * }
 * return "redirect:/exercises"; // Redirect back to the exercises list
 * }
 * 
 * // Display the form to edit a specific review
 * 
 * @GetMapping("/edit/{id}")
 * public String editReview(@PathVariable("id") Long id, Model model) {
 * log.info("Editing review with ID: " + id);
 * Review review = reviewRepository.findById(id).orElse(null);
 * if (review != null) {
 * model.addAttribute("review", review);
 * return "editReview"; // Return the view for editing the review
 * }
 * log.warn("Review with ID: " + id + " not found");
 * return "redirect:/exercises"; // Redirect if not found
 * }
 * 
 * // Handle the submission of the updated review
 * 
 * @PostMapping("/update")
 * public String updateReview(@ModelAttribute("review") Review review) {
 * log.info("Updating review with ID: " + review.getId());
 * reviewRepository.save(review);
 * return "redirect:/exercises"; // Redirect back to the exercises list
 * }
 * 
 * // Handle the deletion of a specific review
 * 
 * @GetMapping("/delete/{id}")
 * public String deleteReview(@PathVariable("id") Long id) {
 * log.info("Deleting review with ID: " + id);
 * reviewRepository.deleteById(id);
 * return "redirect:/exercises"; // Redirect back to the exercises list
 * }
 * 
 * // Optional: Method to show all reviews for a specific exercise
 * 
 * @GetMapping("/exercise/{exerciseId}")
 * public String showReviewsForExercise(@PathVariable("exerciseId") Long
 * exerciseId, Model model) {
 * log.info("Showing reviews for exercise with ID: " + exerciseId);
 * List<Review> reviews = reviewRepository.findByExerciseId(exerciseId);
 * model.addAttribute("reviews", reviews);
 * return "reviewList"; // Return the view that displays the reviews
 * }
 * }
 */