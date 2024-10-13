/*
 * package syksy24.backend.fitness.web;
 * 
 * import syksy24.backend.fitness.model.Review;
 * import syksy24.backend.fitness.model.ReviewRepository;
 * import org.springframework.beans.factory.annotation.Autowired;
 * import org.springframework.http.ResponseEntity;
 * import org.springframework.web.bind.annotation.*;
 * 
 * import java.util.List;
 * 
 * @RestController
 * 
 * @RequestMapping("/api/reviews")
 * public class RestReviewController {
 * 
 * @Autowired
 * private ReviewRepository reviewRepository;
 * 
 * // Get reviews for a specific exercise
 * 
 * @GetMapping("/exercise/{exerciseId}")
 * public List<Review> getReviewsForExercise(@PathVariable Long exerciseId) {
 * return reviewRepository.findByExerciseId(exerciseId);
 * }
 * 
 * // Create a new review
 * 
 * @PostMapping
 * public ResponseEntity<Review> createReview(@RequestBody Review review) {
 * Review savedReview = reviewRepository.save(review);
 * return ResponseEntity.ok(savedReview); // Wrap the saved review in a
 * ResponseEntity
 * }
 * 
 * // Delete a review by ID
 * 
 * @DeleteMapping("/{id}")
 * public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
 * return reviewRepository.findById(id)
 * .map(review -> {
 * reviewRepository.delete(review);
 * return ResponseEntity.noContent().<Void>build(); // Specify <Void> here
 * })
 * .orElse(ResponseEntity.notFound().<Void>build()); // Specify <Void> here
 * }
 * }
 */