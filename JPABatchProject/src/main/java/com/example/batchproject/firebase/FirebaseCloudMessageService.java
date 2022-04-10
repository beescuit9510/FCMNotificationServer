package com.example.batchproject.firebase;

import com.example.batchproject.model.vo.PushMessage;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.WebpushConfig;
import com.google.firebase.messaging.WebpushNotification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutionException;

@Component
@RequiredArgsConstructor
@Slf4j
public class FirebaseCloudMessageService {
//https://velog.io/@skygl/FCM-Spring-Boot를-사용하여-웹-푸시-기능-구현하기
    @PostConstruct
    public void initialize() {
        try {
            if (!FirebaseApp.getApps().isEmpty()) {
                FirebaseDatabase.getInstance().setPersistenceEnabled(true);
            }

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(new ClassPathResource("firebase/notificationreceiver-service-key.json").getInputStream()))
                    .setDatabaseUrl("localhost:8080/")
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public int sendMessageTo(PushMessage pushMessage) throws ExecutionException, InterruptedException {

        Message message = makeMessage(pushMessage);

        String response = FirebaseMessaging.getInstance().sendAsync(message).get();

        log.info(response);

        return 200;

    }


    private Message makeMessage(PushMessage pushMessage) {

        Message message = Message.builder()
                .setToken(pushMessage.getDeviceInformation().getPushTokenKey())
                .setWebpushConfig(WebpushConfig.builder().putHeader("ttl", "300")
                        .setNotification(new WebpushNotification(pushMessage.getTitle(),
                                pushMessage.getBody()))
                        .build())
                .build();

        return message;
    }


}