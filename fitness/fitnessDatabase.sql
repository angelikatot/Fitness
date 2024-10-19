-- Create table for users
CREATE TABLE app_user (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    role VARCHAR(50) DEFAULT 'ROLE_USER' NOT NULL
);

-- Create table for exercises
CREATE TABLE exercise (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    muscle_group VARCHAR(100),
    equipment VARCHAR(100),
    duration INT,
    difficulty_level VARCHAR(50)
);

-- Create table for reviews
CREATE TABLE review (
    id SERIAL PRIMARY KEY,
    reviewer_name VARCHAR(255),
    rating NUMERIC(2, 1) CHECK (rating >= 1 AND rating <= 5),
    comment TEXT,
    exercise_id INT,
    CONSTRAINT fk_exercise FOREIGN KEY (exercise_id) REFERENCES exercise(id) ON DELETE CASCADE
);
