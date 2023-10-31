package com.example.spring_project;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;



@RestController
public class SpringController {
    String email;
    @GetMapping("/image")
    public ResponseEntity<byte[]> getImage() throws IOException {
        Resource resource = new ClassPathResource("static/drini.jpg");

        if (resource.exists()) {
            try (InputStream in = resource.getInputStream()) {
                byte[] imageBytes = IOUtils.toByteArray(in);
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(imageBytes);
            }
        } else {
            // Handle the case when the resource is not found.
            // You can log an error or return an appropriate response.
            return ResponseEntity.notFound().build(); // or return an error response
        }
    }

    @PostMapping("/submit-data")
    public ResponseEntity<String> submitData(@RequestBody Map<String, String> data) {
        // Retrieve data from the frontend
         email = data.get("email");

        // Process the data as needed

        // Return a response
        return ResponseEntity.ok("Data submitted successfully");
    }
    public String getEmail(){
        return this.email;
    }

}
