package syksy24.backend.fitness.model;

import jakarta.persistence.*;

@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String reviewerName;
    private double rating;
    private String comment;

    @ManyToOne
    @JoinColumn(name = "exercise_id")
    private Exercise exercise;

    // Default constructor
    public Review() {
    }

    // Constructor +parameters
    public Review(String reviewerName, double rating, String comment, Exercise exercise) {
        this.reviewerName = reviewerName;
        this.rating = rating;
        this.comment = comment;
        this.exercise = exercise;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReviewerName() {
        return reviewerName;
    }

    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }
}