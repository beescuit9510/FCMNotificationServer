package com.example.batchproject.model.dao;

import com.example.batchproject.model.vo.DeviceInformation;
import com.example.batchproject.model.vo.PushMessage;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MsgDao {

    @Autowired
    private SqlSessionTemplate sqlSession;

    public ArrayList<DeviceInformation> selectDeviceInformation() {
        List<DeviceInformation> deviceinfos = sqlSession.selectList("batch.selectDeviceInformation");
        return (ArrayList<DeviceInformation>)deviceinfos;
    }

    public ArrayList<PushMessage> selectMessage() {
        List<PushMessage> msgs = sqlSession.selectList("batch.selectMessage");
        return (ArrayList<PushMessage>)msgs;
    }

    public int updateMessage(Long pushMessageNo) {
        int result = sqlSession.update("batch.updateMessage", pushMessageNo);
        return result;
    }

}
