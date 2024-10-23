package syksy24.backend.fitness.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Exercise {

    private static final Logger log = LoggerFactory.getLogger(Exercise.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String muscleGroup;
    private String equipment;
    private int duration;
    private String difficultyLevel;

    // one exercise can have many reviews
    @OneToMany(mappedBy = "exercise", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Review> reviews = new ArrayList<>();

    public Exercise() {
    }

    public Exercise(String title, String description, String muscleGroup, String equipment, int duration,
            String difficultyLevel) {
        this.title = title;
        this.description = description;
        this.muscleGroup = muscleGroup;
        this.equipment = equipment;
        this.duration = duration;
        this.difficultyLevel = difficultyLevel;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMuscleGroup() {
        return muscleGroup;
    }

    public void setMuscleGroup(String muscleGroup) {
        this.muscleGroup = muscleGroup;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(String difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public void addReview(Review review) {
        reviews.add(review);
        review.setExercise(this);
    }

    // keskiarvon laskeminen
    public double getAverageRating() {
        log.debug("Calculating average rating for exercise: {}", this.id);
        log.debug("Number of reviews: {}", this.reviews.size());

        if (this.reviews.isEmpty()) {
            log.debug("No reviews found for exercise: {}", this.id);
            return 0.0;
        }

        double sum = 0.0;
        for (Review review : this.reviews) {
            log.debug("Review rating: {}", review.getRating());
            sum += review.getRating();
        }

        double average = sum / this.reviews.size();
        log.debug("Calculated average rating: {}", average);
        return average;
    }

    @Override
    public String toString() {
        return String.format(
                "Exercise[id=%d, title='%s', muscleGroup='%s', difficultyLevel='%s', averageRating=%.1f]",
                id, title, muscleGroup, difficultyLevel, getAverageRating());
    }
}