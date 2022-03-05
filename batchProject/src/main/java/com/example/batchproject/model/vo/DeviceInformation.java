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


//    create table device_information(
//        device_information_no     BIGINT AUTO_INCREMENT PRIMARY KEY,
//        device_os         varchar(100) not null,
//        device_version    varchar(100) not null,
//        push_token_key    varchar(3000) not null,
//        registration_date date not null,
//        UNIQUE (push_token_key)
//        );
//
//        create table push_message(
//        push_message_no BIGINT AUTO_INCREMENT PRIMARY KEY,
//        title VARCHAR(1000) not null,
//        body VARCHAR(1000) not null,
//        image VARCHAR(1000),
//        is_sent BOOLEAN NOT NULL DEFAULT FALSE,
//        push_token_key VARCHAR(3000) not null,
//        FOREIGN KEY(push_token_key) REFERENCES device_information(push_token_key)
//        );
//    default character set utf8 collate utf8_general_ci;
//    insert into push_message(title, body, image, is_sent, push_token_key)
//    VALUES('타이틀입니다','바디입니다','이미지입니다',FALSE,'tokenkey');
//    show full columns from device_information;
//    show full columns from push_message;



