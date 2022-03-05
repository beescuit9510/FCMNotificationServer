package com.example.batchproject.model.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;


@Getter
@Setter
@NoArgsConstructor
@ToString
public class StepDataBean {
    ArrayList<PushMessage> pushMessages;
}
