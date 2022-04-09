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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
@Slf4j
public class FirebaseCloudMessageService {
    private ObjectMapper objectMapper = new ObjectMapper();
    private final MediaType MEDIA_TYPE_JSON = MediaType.get("application/json; charset=utf-8");
    private final String BODY_CONTENT_TYPE = "application/json; UTF-8";
    private final String SCOPE_OF_SERVICE = "https://www.googleapis.com/auth/cloud-platform";
    private final String API_URL = "https://fcm.googleapis.com/v1/projects/PROJECT_ID/messages:send";

    @Value("${firebase.configuration-file}")
    private String firebaseConfigPath;

    @Value("${firebase.project-id}")
    private String projectId;


    public int sendMessageTo(PushMessage pushMessage) throws IOException {

        String apiUrlToSendMessagesTo = API_URL.replace("PROJECT_ID",projectId);

        String message = makeMessage(pushMessage);

        log.info(message);

        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = RequestBody.create(message,MEDIA_TYPE_JSON);

        String authorization = "Bearer "+getAccessToken();

        Request request = new Request.Builder()
                .url(apiUrlToSendMessagesTo)
                .post(requestBody)
                .addHeader(HttpHeaders.AUTHORIZATION,authorization)
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
                        .token(pushMessage.getDeviceInformation().getPushTokenKey())
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

    //기존 API key(Server key) 대신에 Access token을 이용해서 인증하도록 변경됨.
    //GoogleGredentials 인스턴스를 받아옴(구글 api 사용을 위해 oauth2를 이용해 인증한 대상을 나타내는 객체)
    private String getAccessToken() throws IOException {

        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())
                .createScoped(Arrays.asList(SCOPE_OF_SERVICE));

        googleCredentials.refreshIfExpired();

        return googleCredentials.getAccessToken().getTokenValue();
    }
}