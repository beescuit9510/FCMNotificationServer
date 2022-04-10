package com.example.batchproject.tasklet.sender;

import com.example.batchproject.firebase.FirebaseCloudMessageService;
import com.example.batchproject.model.repository.messageRepository;
import com.example.batchproject.model.vo.PushMessage;
import com.example.batchproject.model.vo.StepDataBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
public class MessageSender implements Tasklet {

    private messageRepository msgService;
    private StepDataBean stepDataBean;
    private FirebaseCloudMessageService firebaseFCMService;

    @Autowired
    public MessageSender(messageRepository msgService, StepDataBean stepDataBean, FirebaseCloudMessageService firebaseFCMService) {
        this.msgService = msgService;
        this.stepDataBean = stepDataBean;
        this.firebaseFCMService = firebaseFCMService;
    }


    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {

        List<PushMessage> messages = stepDataBean.getPushMessages();

        if (messages.size() == 0) return RepeatStatus.FINISHED;

        for (PushMessage message : messages) {

            log.info(message.toString());

            updateIsSentTrue(message);


        }
        return RepeatStatus.FINISHED;

    }

    private void updateIsSentTrue(PushMessage message) {
        msgService.updateIsSentTrue(message.getPushMessageNo());
    }
}

