package com.example.spring_project.Controller;

import com.example.spring_project.Model.User;
import com.example.spring_project.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
@Controller
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam String username, @RequestParam String password, Model model) {

        List<User> users = userRepository.findByUsername(username);

        if (!users.isEmpty()) {
            User user = users.get(0);
            if (user.getPassword().equals(password)) {

                return "redirect:/";
            }
        }
        model.addAttribute("error", "Invalid username or password");
        return "login";
    }
}

