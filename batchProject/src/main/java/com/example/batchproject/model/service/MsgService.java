package com.example.batchproject.model.service;

import com.example.batchproject.model.dao.MsgDao;
import com.example.batchproject.model.vo.DeviceInformation;
import com.example.batchproject.model.vo.PushMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MsgService {

    @Autowired
    MsgDao msgDao;

    public ArrayList<DeviceInformation> selectDeviceInformation() {
        return msgDao.selectDeviceInformation();
    }

    public ArrayList<PushMessage> selectMessage() {
        return msgDao.selectMessage();
    }

    public int updateMessage(Long pushMessageNo) {
        return msgDao.updateMessage(pushMessageNo);
    }

}
