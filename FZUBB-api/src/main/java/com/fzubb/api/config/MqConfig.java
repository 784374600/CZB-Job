package com.fzubb.api.config;

import com.alibaba.fastjson.JSONObject;
import com.fzubb.common.ExcepTion.MessageUnhandleException;
import com.fzubb.common.constant.MQConstant;
import com.fzubb.common.mq.MessageHandler;
import org.apache.dubbo.common.logger.Logger;
import org.apache.dubbo.common.logger.LoggerFactory;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.context.annotation.Bean;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MqConfig {
    static Logger logger= LoggerFactory.getLogger(MqConfig.class);
    @Bean(initMethod = "start",destroyMethod ="shutdown" )
    public DefaultMQProducer mqProducer(){
        DefaultMQProducer producer = new DefaultMQProducer("FZUBBProducer");
        producer.setNamesrvAddr("localhost:9876");
        producer.setRetryTimesWhenSendAsyncFailed(3);
        return  producer;
    }
    @Bean(initMethod = "start")
    public DefaultMQPushConsumer mqConsumer()throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("FZUBBConsumer");

        // Specify name server addresses.
        consumer.setNamesrvAddr("localhost:9876");

        // Subscribe one more more topics to consume.
        consumer.subscribe(MQConstant.FZUBB_Topic, "*");
        // Register callback to execute on arrival of messages fetched from brokers.
        consumer.registerMessageListener(new MessageListenerConcurrently() {

            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                                                            ConsumeConcurrentlyContext context) {
                if(msgs==null ||msgs.size()==0)
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                for(int i=0;i<msgs.size();i++){
                    MessageExt msg=msgs.get(i);
                    try {
                        getMessageHandler(msg).handle(msg.getBody());
                    } catch (MessageUnhandleException e) {
                         logger.warn(MessageFormat.format("消息消费失败 topic:{0} tags:{1} body:{2}", msg.getTopic(),msg.getTags(),JSONObject.parse(msg.getBody())));
                         return  ConsumeConcurrentlyStatus.RECONSUME_LATER;
                    }
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        return  consumer;
    }

    private Map<String, MessageHandler> handlerMappings=new HashMap<>();
    private MessageHandler  getMessageHandler(MessageExt msg){
              return handlerMappings.get(msg.getTags());
    }

}
