package com.fzubb.mq.message.handler;

import com.alibaba.fastjson.JSONObject;
import com.fzubb.ExcepTion.MessageUnhandleException;
import com.fzubb.mapper.CourseMapper;
import com.fzubb.mq.Message.UpdateSCMessage;
import com.fzubb.mq.MessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Component
public class UpdateSCMessageHandler implements MessageHandler
{
    @Autowired
    CourseMapper courseMapper;
    @Override
    @Transactional
    public void handle(byte[] message) throws MessageUnhandleException {
        try {
            UpdateSCMessage msg= JSONObject.parseObject(message, UpdateSCMessage.class );
            courseMapper.deleteSC(msg.getId());
            if(msg.getCourses()!=null)
            courseMapper.updateSC(msg.getId(),msg.getCourses());
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw  new MessageUnhandleException();
        }
    }
}
