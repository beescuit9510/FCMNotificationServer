package com.example.batchproject.tasklet.sender;

import com.example.batchproject.firebase.FirebaseCloudMessageService;
import com.example.batchproject.model.service.MessageService;
import com.example.batchproject.model.vo.PushMessage;
import com.example.batchproject.model.vo.StepDataBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;

@Slf4j
public class MessageSender implements Tasklet {

    private MessageService msgService;
    private StepDataBean stepDataBean;
    private FirebaseCloudMessageService firebaseFCMService;

    private static final int OK_STATUS_CODE = 200;

    @Autowired
    public MessageSender(MessageService msgService, StepDataBean stepDataBean, FirebaseCloudMessageService firebaseFCMService) {
        this.msgService = msgService;
        this.stepDataBean = stepDataBean;
        this.firebaseFCMService = firebaseFCMService;
    }


    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {

        ArrayList<PushMessage> messages = stepDataBean.getPushMessages();

        if(messages.size()==0) return RepeatStatus.FINISHED;

        for (PushMessage message : messages) {

            int statusCode = firebaseFCMService.sendMessageTo(message);

            log.info(message.toString());
            log.info("STATUS CODE : "+statusCode);

            updateIsSentTrue(statusCode, message);


        }
        return RepeatStatus.FINISHED;

    }

    private void updateIsSentTrue(int statusCode, PushMessage message){
        if (statusCode==OK_STATUS_CODE){
            msgService.updateIsSentTrue(message.getPushMessageNo());
        }
    }
}

