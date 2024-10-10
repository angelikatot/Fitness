package syksy24.backend.fitness.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.List;

@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private double rating;
    private String muscleGroup;
    private String equipment;
    private int duration;
    private String difficultyLevel;

    @ElementCollection
    private List<String> tags;

    // konstruktori
    public Exercise() {
    }

    // konstruktori ja parametrit
    public Exercise(String title, String description, double rating, String muscleGroup, String equipment, int duration,
            String difficultyLevel, List<String> tags) {
        this.title = title;
        this.description = description;
        this.rating = rating;
        this.muscleGroup = muscleGroup;
        this.equipment = equipment;
        this.duration = duration;
        this.difficultyLevel = difficultyLevel;
        this.tags = tags;
    }

    // get, set

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

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
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

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return String.format(
                "Exercise[id=%d, title='%s', muscleGroup='%s', difficultyLevel='%s']",
                id, title, muscleGroup, difficultyLevel);
    }
}
