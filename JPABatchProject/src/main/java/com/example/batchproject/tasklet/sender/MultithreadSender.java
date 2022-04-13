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
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MultithreadSender extends Thread {

    private messageRepository msgRepository;
    private StepDataBean stepDataBean;
    private FCMService fcmService;
    private int start;
    private int maxMsgPerThread;

    @Autowired
    private MultithreadSender(messageRepository msgRepository, StepDataBean stepDataBean, FCMService fcmService) {
        this.msgRepository = msgRepository;
        this.stepDataBean = stepDataBean;
        this.fcmService = fcmService;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public void setMaxMsgPerThread(int maxMsgPerThread) {
        this.maxMsgPerThread = maxMsgPerThread;
    }

    @Override
    public void run() {

        List<PushMessage> messages = stepDataBean.getPushMessages();

        int end = start+maxMsgPerThread;

        for(start++; start>end; start++){

            System.out.println("--------------------------------");
            System.out.println("--------------------------------");
            System.out.println("--------------------------------");
            System.out.println("--------------------------------");
            System.out.println("--------------------------------");
            System.out.println("--------------------------------");
            System.out.println("--------------------------------");
            System.out.println("start "+start);
            System.out.println("--------------------------------");
            System.out.println("--------------------------------");
            System.out.println("--------------------------------");
            System.out.println("--------------------------------");
            System.out.println("--------------------------------");
            System.out.println("--------------------------------");
            System.out.println("--------------------------------");
            System.out.println("--------------------------------");
            PushMessage message = messages.get(start);

            if(message==null) return;

            String response = sendMessage(message);

            if(response!=null) updateIsSentTrue(message);


        }



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

    private void updateIsSentTrue(PushMessage pushMessage) {

        msgRepository.updateIsSentTrue(pushMessage.getPushMessageNo());

    }

}

