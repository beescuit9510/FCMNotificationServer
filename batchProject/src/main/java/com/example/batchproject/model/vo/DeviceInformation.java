package com.example.batchproject.model.vo;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DeviceInformation {
    private Long deviceInformationNo;
    private String deviceOs;
    private String deviceVersion;
    private String pushTokenKey;
    private String registrationDate;
}

