package com.example.batchproject.model.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Component
public class StepDataBean {

    List<PushMessage> pushMessages;
}
