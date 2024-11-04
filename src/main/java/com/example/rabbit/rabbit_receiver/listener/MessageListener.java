package com.example.rabbit.rabbit_receiver.listener;

import com.example.rabbit.rabbit_receiver.dto.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MessageListener {

    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange(value = "message-exchange",
                    type = ExchangeTypes.DIRECT),
            value = @Queue(value = "message-queue"), key = "key123"))
    public void receiveMessage(String message){
        log.info("Received message: {}", message);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "department_messages_queue"),
            exchange = @Exchange(value = "${mq.message.topic.exchange}", type = ExchangeTypes.TOPIC),
            key = "department.#"
    ))
    public void receiveMessageFromDepartment(Message message){
        log.info(message.getTitle(), message.getContent());
    }

}
