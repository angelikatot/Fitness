package syksy24.backend.fitness.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import syksy24.backend.fitness.model.User;
import syksy24.backend.fitness.model.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/signup")
    public String signupForm(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute("user") User user, Model model) {
        // Check if username already exists
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            model.addAttribute("message", "Username already exists");
            return "signup";
        }

        // Encode password and set role
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("USER");

        // Save user
        userRepository.save(user);

        model.addAttribute("message", "Sign up successful. Please log in.");
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}