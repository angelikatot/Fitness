package syksy24.backend.fitness.model;

import org.springframework.data.jpa.repository.JpaRepository;
import syksy24.backend.fitness.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
