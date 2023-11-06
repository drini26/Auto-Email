package com.example.spring_project;

import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.resource.Emailv31;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Base64;

@Service
public class MailjetService {
    /**
     * This call sends a message to the given recipient with inline attachment.
     */

    @Autowired
    private SpringController springController;

    @Value("${mailjet.apiKeyPublic}")
    private String apiKeyPublic;

    @Value("${mailjet.apiKeyPrivate}")
    private String apiKeyPrivate;

        public void sendEmail() throws MailjetException {
            MailjetClient client;
            MailjetRequest request;
            MailjetResponse response;
            String email = springController.getEmail();
            String url = springController.getUrl();
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
                                                        .put("Email",email)
                                                        .put("Name", "Drini")))
                                        .put(Emailv31.Message.SUBJECT, "Your photo!")
                                        .put(Emailv31.Message.TEXTPART, "This is the photo you selected from our website")
                                        .put(Emailv31.Message.INLINEDATTACHMENTS, new JSONArray()
                                                .put(new JSONObject()
                                                        .put("ContentType", "image/jpg")
                                                        .put("Filename", "ImageViewer.jpg")
                                                        .put("ContentID", "id1")
                                                        .put("Base64Content", getBase64ImageFromURL(url) )))));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            response = client.post(request);
            System.out.println(response.getStatus());
            System.out.println(response.getData());

        }



    private static String getBase64ImageFromURL(String imageUrl) throws IOException {
        try {
            URL url = new URL(imageUrl);
            URLConnection connection = url.openConnection();
            try (InputStream inputStream = connection.getInputStream()) {
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                byte[] imageBytes = outputStream.toByteArray();
                return Base64.getEncoder().encodeToString(imageBytes);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Invalid URL: " + imageUrl, e);
        }
    }

}
