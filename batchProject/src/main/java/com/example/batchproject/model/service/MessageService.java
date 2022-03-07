package com.example.batchproject.model.service;

import com.example.batchproject.model.dao.MessageDao;
import com.example.batchproject.model.vo.DeviceInformation;
import com.example.batchproject.model.vo.PushMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MessageService {

    MessageDao messageDao;

    @Autowired
    public MessageService(MessageDao msgDao) {
        this.messageDao = msgDao;
    }

    public ArrayList<DeviceInformation> selectDeviceInformation() {
        return messageDao.selectDeviceInformation();
    }

    public ArrayList<PushMessage> selectMessage() {return messageDao.selectMessage();}

    public int updateMessage(Long pushMessageNo) {
        return messageDao.updateMessage(pushMessageNo);
    }

}
