package com.example.batchproject.model.dao;

import com.example.batchproject.model.vo.DeviceInformation;
import com.example.batchproject.model.vo.PushMessage;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class MessageDao {

    private SqlSessionTemplate sqlSession;

    @Autowired
    public MessageDao(SqlSessionTemplate sqlSession) {
        this.sqlSession = sqlSession;
    }

    public ArrayList<DeviceInformation> selectDeviceInformation() {
        List<DeviceInformation> deviceInfos = sqlSession.selectList("batch.selectDeviceInformation");
        return (ArrayList<DeviceInformation>)deviceInfos;
    }

    public ArrayList<PushMessage> selectMessageToSend() {
        List<PushMessage> messages = sqlSession.selectList("batch.selectMessageToSend");
        return (ArrayList<PushMessage>)messages;
    }

    public int updateIsSentTrue(Long pushMessageNo) {
        int result = sqlSession.update("batch.updateIsSentTrue", pushMessageNo);
        return result;
    }

}
