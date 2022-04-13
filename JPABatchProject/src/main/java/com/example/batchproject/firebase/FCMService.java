package com.example.batchproject.firebase;

import com.example.batchproject.model.vo.PushMessage;
import com.google.firebase.messaging.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FCMService {

    public String sendMessageTo(PushMessage pushMessage) throws FirebaseMessagingException {

        Message message = makeMessage(pushMessage);

        String response = FirebaseMessaging.getInstance().send(message);

        return response;

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