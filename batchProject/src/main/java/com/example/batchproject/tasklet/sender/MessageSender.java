package com.example.batchproject.tasklet.sender;

import com.example.batchproject.firebase.FirebaseCloudMessageService;
import com.example.batchproject.model.service.MsgService;
import com.example.batchproject.model.vo.PushMessage;
import com.example.batchproject.model.vo.StepDataBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Slf4j
public class MessageSender implements Tasklet, StepExecutionListener {

    @Autowired
    MsgService msgService;

    @Autowired
    StepDataBean stepDataBean;

    @Autowired
    FirebaseCloudMessageService firebaseFCMService;

    @Override
    public void beforeStep(StepExecution stepExecution) {
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return null;
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {

        ArrayList<PushMessage> messages = stepDataBean.getPushMessages();

        if(messages==null) return RepeatStatus.FINISHED;


        for (PushMessage message : messages) {

            int statusCode = firebaseFCMService.sendMessageTo(message);

            log.info(message.toString());

            updateMessage(statusCode, message);


        }
        return RepeatStatus.FINISHED;

    }

    private void updateMessage(int statusCode, PushMessage message){
        if (statusCode==400){
            msgService.updateMessage(message.getPushMessageNo());
        }
    }
}
