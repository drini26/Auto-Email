package com.example.spring_project;

import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.resource.Emailv31;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

@Service
public class MailjetService {
    /**
     * This call sends a message to the given recipient with inline attachment.
     */

    @Value("${mailjet.apiKeyPublic}")
    private String apiKeyPublic;

    @Value("${mailjet.apiKeyPrivate}")
    private String apiKeyPrivate;

        public void sendEmail() throws MailjetException {
            MailjetClient client;
            MailjetRequest request;
            MailjetResponse response;


            client = new MailjetClient(apiKeyPublic, apiKeyPrivate);
            try {
                request = new MailjetRequest(Emailv31.resource)
                        .property(Emailv31.MESSAGES, new JSONArray()
                                .put(new JSONObject()
                                        .put(Emailv31.Message.FROM, new JSONObject()
                                                .put("Email", "drinitelharaj@gmail.com")
                                                .put("Name", "Drini Telharaj"))
                                        .put(Emailv31.Message.TO, new JSONArray()
                                                .put(new JSONObject()
                                                        .put("Email", new SpringController().getEmail())
                                                        .put("Name", "Drini")))
                                        .put(Emailv31.Message.SUBJECT, "Your photo!")
                                        .put(Emailv31.Message.TEXTPART, "This is the photo you selected from our website")
                                        .put(Emailv31.Message.INLINEDATTACHMENTS, new JSONArray()
                                                .put(new JSONObject()
                                                        .put("ContentType", "image/jpg")
                                                        .put("Filename", "drini.jpg")
                                                        .put("ContentID", "id1")
                                                        .put("Base64Content", getBase64ImageContent() )))));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            response = client.post(request);
            System.out.println(response.getStatus());
            System.out.println(response.getData());
        }



    private static String getBase64ImageContent() throws IOException {
        byte[] fileContent = Files.readAllBytes(Paths.get("src/main/resources/static/drini.jpg"));
        return Base64.getEncoder().encodeToString(fileContent);
    }
}
