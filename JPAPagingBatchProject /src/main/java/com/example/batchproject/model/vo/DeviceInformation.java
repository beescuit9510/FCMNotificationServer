package com.example.batchproject.model.vo;


import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "device_information")
public class DeviceInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "device_information_no")
    private Long deviceInformationNo;

    @Column(name = "device_os")
    private String deviceOs;

    @Column(name = "device_version")
    private String deviceVersion;

    @Column(name = "push_token_key")
    private String pushTokenKey;

    @Column(name = "registration_date")
    private String registrationDate;
}

