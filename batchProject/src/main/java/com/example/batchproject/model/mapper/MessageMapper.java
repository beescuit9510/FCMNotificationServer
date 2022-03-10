package com.example.batchproject.model.mapper;

import com.example.batchproject.model.vo.DeviceInformation;
import com.example.batchproject.model.vo.PushMessage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MessageMapper {

    List<DeviceInformation> selectDeviceInformation();

    List<PushMessage> selectMessageToSend();

    int updateIsSentTrue(Long pushMessageNo);
}
