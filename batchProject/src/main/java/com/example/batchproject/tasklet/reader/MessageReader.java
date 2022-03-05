package com.example.batchproject.tasklet.reader;

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
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;


@Slf4j
public class MessageReader implements Tasklet, StepExecutionListener {

    @Autowired
    MsgService msgService;

    @Autowired
    StepDataBean stepDataBean;



    @Override
    public void beforeStep(StepExecution stepExecution) {
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return null;
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {

        ArrayList<PushMessage> messages = msgService.selectMessage();

        stepDataBean.setPushMessages(messages);

        stepContribution.setExitStatus(ExitStatus.COMPLETED);

        log.info("msg size : "+messages.size());
        messages.forEach(msg->log.info(msg.toString()));

        return RepeatStatus.FINISHED;
    }

}