package com.example.batchproject.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.FirebaseDatabase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.InputStream;

@Component
@Slf4j
public class FCMInitializer {

    @Value("${firebase.configuration-path}")
    private String configPath;

    @PostConstruct
    private void initialize() {
        try {

            if (!FirebaseApp.getApps().isEmpty()) {
                FirebaseDatabase.getInstance().setPersistenceEnabled(true);
            }

            InputStream configPath = new ClassPathResource(this.configPath).getInputStream();
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(configPath))
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
