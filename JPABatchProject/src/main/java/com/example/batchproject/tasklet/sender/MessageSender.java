package com.example.batchproject.tasklet.sender;

import com.example.batchproject.firebase.FCMService;
import com.example.batchproject.model.repository.messageRepository;
import com.example.batchproject.model.vo.PushMessage;
import com.example.batchproject.model.vo.StepDataBean;
import com.google.firebase.messaging.FirebaseMessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class MultithreadMessageSender implements Tasklet {

    private StepDataBean stepDataBean;
    private messageRepository msgRepository;
    private FCMService fcmService;


    @Autowired
    public MultithreadMessageSender(StepDataBean stepDataBean, messageRepository msgRepository, FCMService fcmService) {
        this.msgRepository = msgRepository;
        this.stepDataBean = stepDataBean;
        this.fcmService = fcmService;
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) {

        List<PushMessage> pushMessages = stepDataBean.getPushMessages();

        boolean isEmpty = pushMessages.size()==0;

        if (isEmpty) return RepeatStatus.FINISHED;


        for(PushMessage pushMessage : pushMessages){

            String response = sendMessage(pushMessage);

            updateIsSentTrue(response, pushMessage);

        }

        return RepeatStatus.FINISHED;

    }


    private String sendMessage(PushMessage pushMessage) {

        String response = null;

        try {

            response = fcmService.sendMessageTo(pushMessage);

        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
        }

        return response;
    }

    private void updateIsSentTrue(String response, PushMessage pushMessage) {

        if(response!=null){

            msgRepository.updateIsSentTrue(pushMessage.getPushMessageNo());

        }

    }


}

