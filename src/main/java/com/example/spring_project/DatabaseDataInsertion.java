package com.example.spring_project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.File;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Service
public class DatabaseDataInsertion {
    @Autowired
    private SpringController springController;

    private final DataSource dataSource;

    @Autowired
    public DatabaseDataInsertion(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void addEmailsToDatabase() {
        String email = springController.getEmail();
        String url = springController.getUrl();
        int startIndex = url.indexOf('(');
        int endIndex = url.indexOf(')');
        String idString = url.substring(startIndex + 1, endIndex);
        int id = Integer.parseInt(idString);

        try (var connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO emails_sent (sent_to, image_sent, time_sent) VALUES (?, ?, CURRENT_TIMESTAMP)")) {
            preparedStatement.setString(1, email);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addImagesToDatabase() {
        String directoryPath = "src/main/resources/static/wallpapers";
        File directory = new File(directoryPath);

        try (var connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO images (file_name, file_data, upload_date) VALUES (?, ?, CURRENT_TIMESTAMP)")) {
            for (File file : directory.listFiles()) {
                preparedStatement.setString(1, file.getName());
                preparedStatement.setLong(2, file.length());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
