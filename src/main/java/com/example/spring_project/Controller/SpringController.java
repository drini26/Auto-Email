package com.example.spring_project.Controller;

import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@RestController
public class SpringController {
    String email;
    String url;

    @PostMapping("/submit-data")
    public ResponseEntity<String> submitData(@RequestBody Map<String, String> data) {
        email = data.get("email");
        return ResponseEntity.ok("Data submitted successfully");
    }
    @GetMapping("/images")
    public ResponseEntity<List<String>> getImageUrls() {
        List<String> imageUrls = new ArrayList<>();
        for (int i = 1; i <= 8; i++) {
            String imageUrl = "http://localhost:8080/wallpapers/wallpaper("+i+").jpg";
            imageUrls.add(imageUrl);
        }
        if (!imageUrls.isEmpty()) {
            return ResponseEntity.ok(imageUrls);
        } else {
            return ResponseEntity.notFound().build();
        }

    }
    @PostMapping ("/selectedImage")
    public ResponseEntity<String> receiveSelectedImageUrl(@RequestBody Map<String, String> requestData) {
        url = requestData.get("selectedImageUrl");
        return ResponseEntity.ok("Received selected image URL: " + url);
    }

}
