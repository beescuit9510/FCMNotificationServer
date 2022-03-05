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
public class MessageSender implements Tasklet{

    @Autowired
    MessageService msgService;

    @Autowired
    StepDataBean stepDataBean;

    @Autowired
    FirebaseCloudMessageService firebaseFCMService;
    private final int OK_STATUS_CODE = 200;

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {

        ArrayList<PushMessage> messages = stepDataBean.getPushMessages();

        if(messages==null) return RepeatStatus.FINISHED;


        for (PushMessage message : messages) {

            int statusCode = firebaseFCMService.sendMessageTo(message);

            log.info(message.toString());
            log.info("STATUS CODE : "+statusCode);

            updateMessage(statusCode, message);


        }
        return RepeatStatus.FINISHED;

    }

    private void updateMessage(int statusCode, PushMessage message){
        if (statusCode==OK_STATUS_CODE){
            msgService.updateMessage(message.getPushMessageNo());
        }
    }
}
