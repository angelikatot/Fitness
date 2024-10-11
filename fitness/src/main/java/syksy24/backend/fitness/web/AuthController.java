package syksy24.backend.fitness.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import syksy24.backend.fitness.model.User;
import syksy24.backend.fitness.model.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/signup")
    public String signupForm() {
        return "signup"; // return signup.html
    }

    @PostMapping("/signup")
    public String signup(User user) {
        user.setRole("USER"); // Set default role as a String
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword())); // Encode password
        userRepository.save(user);
        return "redirect:/login"; // Redirect to login after signup
    }

    @GetMapping("/login") // Added method to serve the login page
    public String login() {
        return "login"; // return login.html
    }
}
