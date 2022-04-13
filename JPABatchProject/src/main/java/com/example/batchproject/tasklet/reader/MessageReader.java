package com.example.batchproject.tasklet.reader;

import com.example.batchproject.model.repository.messageRepository;
import com.example.batchproject.model.vo.PushMessage;
import com.example.batchproject.model.vo.StepDataBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@Slf4j
public class MessageReader implements Tasklet {

    private messageRepository msgRepository;
    private StepDataBean stepDataBean;

    @Autowired
    public MessageReader(messageRepository msgRepository, StepDataBean stepDataBean) {
        this.msgRepository = msgRepository;
        this.stepDataBean = stepDataBean;
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext){

        List<PushMessage> messages = msgRepository.selectMessageToSend();

        stepDataBean.setPushMessages(messages);

        stepContribution.setExitStatus(ExitStatus.COMPLETED);

        log.info("msg size : "+messages.size());

        messages.forEach(msg->log.info(msg.toString()));

        return RepeatStatus.FINISHED;
    }

}
