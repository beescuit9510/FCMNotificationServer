package com.example.batchproject.firebase;

import com.example.batchproject.model.vo.PushMessage;
import com.google.api.core.ApiFuture;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.WebpushConfig;
import com.google.firebase.messaging.WebpushNotification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.concurrent.ExecutionException;

@Component
@Slf4j
public class FCMService {

    public int sendMessageTo(PushMessage pushMessage) throws ExecutionException, InterruptedException {

        Message message = makeMessage(pushMessage);

        ApiFuture response = FirebaseMessaging.getInstance().sendAsync(message);

        System.out.println(response.isDone()+"ASDFASDFASDFASDFSADQ#WEQWEQWEQWRWERQWRE");


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