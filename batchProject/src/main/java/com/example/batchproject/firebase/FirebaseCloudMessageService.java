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

//    @Value("${app.firebase-configuration-file}")
    private static String firebaseConfigPath = "firebase/firebase_service_key.json";
    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private static final String CONTENT_TYPE = "application/json; UTF-8";

//    private final String API_URL = "https://fcm.googleapis.com/v1/projects/PROJECT_ID/messages:send";

    private final String API_URL = "https://fcm.googleapis.com/v1/projects/test-335bc/messages:send";


    public static FirebaseCloudMessageService createFirebaseCloudMessageService() {
        return new FirebaseCloudMessageService();
    }

    public int sendMessageTo(PushMessage pushMessage) throws IOException {
        String message = makeMessage(pushMessage);

        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(message,JSON);

        Request request = new Request.Builder()
                .url(API_URL)
                .post(requestBody)
                .addHeader(HttpHeaders.AUTHORIZATION,"Bearer "+getAccessToken())
                .addHeader(HttpHeaders.CONTENT_TYPE, CONTENT_TYPE)
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
                .fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())
                .createScoped(Arrays.asList("https://www.googleapis.com/auth/cloud-platform"));

        googleCredentials.refreshIfExpired();

        return googleCredentials.getAccessToken().getTokenValue();
    }
}