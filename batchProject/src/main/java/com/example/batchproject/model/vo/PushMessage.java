package com.example.batchproject.model.vo;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PushMessage {
    private  Long pushMessageNo;
    private  String title;
    private  String body;
    private  String image;
    private  Integer isSent;
    private  Long deviceInformationNo;
    private String pushTokenKey;

}
