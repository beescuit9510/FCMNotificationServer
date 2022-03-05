package com.example.batchproject.firebase;

import com.example.batchproject.model.vo.FcmMessage;
import com.example.batchproject.model.vo.PushMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.http.HttpHeaders;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
@Slf4j
public class FirebaseCloudMessageService {
    private ObjectMapper objectMapper = new ObjectMapper();
    private final String FIREBASE_CONFIG_PATH = "firebase/firebase_service_key.json";
    private final MediaType MEDIA_TYPE_JSON = MediaType.get("application/json; charset=utf-8");
    private final String BODY_CONTENT_TYPE = "application/json; UTF-8";
    private final String PROJECT_ID = "test-335bc";
    private final String API_URL = "https://fcm.googleapis.com/v1/projects/"+PROJECT_ID+"/messages:send";
    private final String SCOPE = "https://www.googleapis.com/auth/cloud-platform";



    public int sendMessageTo(PushMessage pushMessage) throws IOException {
        String message = makeMessage(pushMessage);

        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(message,MEDIA_TYPE_JSON);

        final String AUTHORIZATION = "Bearer "+getAccessToken();

        Request request = new Request.Builder()
                .url(API_URL)
                .post(requestBody)
                .addHeader(HttpHeaders.AUTHORIZATION,AUTHORIZATION)
                .addHeader(HttpHeaders.CONTENT_TYPE, BODY_CONTENT_TYPE)
                .build();

        Response response = client.newCall(request)
                .execute();

        log.info(response.body().string());

        int statusCode = response.code();

        return statusCode;
    }

    private String makeMessage(PushMessage pushMessage) throws JsonProcessingException {
        FcmMessage fcmMessage = FcmMessage.builder()
                .message(FcmMessage.Message.builder()
                        .token(pushMessage.getPushTokenKey())
                        .notification(
                                FcmMessage.Notification.builder()
                                .title(pushMessage.getTitle())
                                .body(pushMessage.getBody())
                                .image(pushMessage.getImage())
                                .build()
                        )
                        .build()
                )
                .validate_only(false)
                .build();


        return objectMapper.writeValueAsString(fcmMessage);
    }


    private String getAccessToken() throws IOException {

        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new ClassPathResource(FIREBASE_CONFIG_PATH).getInputStream())
                .createScoped(Arrays.asList(SCOPE));

        googleCredentials.refreshIfExpired();

        return googleCredentials.getAccessToken().getTokenValue();
    }
}