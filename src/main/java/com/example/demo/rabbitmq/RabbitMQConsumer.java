package com.example.demo.rabbitmq;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RabbitMQConsumer{

    private final RabbitTemplate rabbitTemplate;

//    @RabbitListener(queues = {"${rabbitmq.queue.name}"})
//    public void consumeMessage(String message){
//        log.info("Received message:" + message.toString());
//    }
//
//    @RabbitListener(queues = {"${rabbitmq.queue.json.name}"})
//    public void consumeJsonMessage(Immovables immovables){
//        log.info("Received message:" + immovables.toString());
//    }
    public String getMessageFromQueue(){
        log.info("get");
        return (String)rabbitTemplate.receiveAndConvert("javaquides");

    }

}
