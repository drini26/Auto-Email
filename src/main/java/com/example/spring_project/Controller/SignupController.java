package com.example.spring_project.Controller;

import com.example.spring_project.Model.User;
import com.example.spring_project.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class SignupController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/signup")
    public String processSignup(@ModelAttribute("user") User user, Model model) {

        List<User> existingUsers = userRepository.findByUsername(user.getUsername());

        if (!existingUsers.isEmpty()) {

            model.addAttribute("error", "Username is already taken. Please choose another.");
            return "signup";
        }

        userRepository.save(user);
        return "redirect:/login";
    }
}
