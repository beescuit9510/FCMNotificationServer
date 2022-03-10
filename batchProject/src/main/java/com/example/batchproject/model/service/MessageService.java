package com.example.batchproject.model.service;

import com.example.batchproject.model.mapper.MessageMapper;
import com.example.batchproject.model.vo.DeviceInformation;
import com.example.batchproject.model.vo.PushMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MessageService {

    private final MessageMapper messageMapper;

    @Autowired
    public MessageService(MessageMapper messageMapper) {
        this.messageMapper = messageMapper;
    }

    public List<DeviceInformation> selectDeviceInformation() {
        return messageMapper.selectDeviceInformation();
    }

    public List<PushMessage> selectMessageToSend() {
        return messageMapper.selectMessageToSend();
    }

    public int updateIsSentTrue(Long pushMessageNo) {
        return messageMapper.updateIsSentTrue(pushMessageNo);
    }

}
