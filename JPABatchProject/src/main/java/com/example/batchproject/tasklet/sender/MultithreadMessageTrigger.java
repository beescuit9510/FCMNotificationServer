package com.example.batchproject.tasklet.sender;

import com.example.batchproject.model.vo.StepDataBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MultithreadMessageTrigger implements Tasklet {

    private StepDataBean stepDataBean;
    private MultithreadSender multithreadSender;

    @Autowired
    public MultithreadMessageTrigger(StepDataBean stepDataBean, MultithreadSender multithreadSender) {
        this.stepDataBean = stepDataBean;
        this.multithreadSender = multithreadSender;
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) {

        int messageSize = stepDataBean.getPushMessages().size();

        boolean isEmpty = messageSize==0;

        if (isEmpty) return RepeatStatus.FINISHED;

        int maxMsgPerThread = 25;
        int last = messageSize % maxMsgPerThread;
        int first = 0;

        System.out.println("fasdfasdfasdf :"+first+"dsfasdfasd : "+last);

        for(first++;first<last;first++){
            System.out.println("fasdfasdfasdf aaa" +first);

            int start = maxMsgPerThread*first;

            multithreadSender.setStart(start);
            multithreadSender.setMaxMsgPerThread(maxMsgPerThread);
            multithreadSender.run();
//            MultithreadSender sender = MultithreadSender;

//            sender.start();

        }

        return RepeatStatus.FINISHED;

    }


}

