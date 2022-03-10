package com.example.batchproject.model.service;

import com.example.batchproject.model.vo.DeviceInformation;
import com.example.batchproject.model.vo.PushMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Repository
public interface IMessageService {

    ArrayList<DeviceInformation> selectDeviceInformation() throws Exception;

    ArrayList<PushMessage> selectMessageToSend() throws Exception;

    int updateIsSentTrue(Long pushMessageNo) throws Exception;

}
