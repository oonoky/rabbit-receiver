package com.example.rabbit.rabbit_receiver.config;

import com.example.rabbit.rabbit_receiver.dto.OrderDTO;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitConfig {

    @Bean
    public Queue almatyOrdersQueue() {
        return QueueBuilder.durable("almaty_orders_queue")
                .withArgument("x-dead-letter-exchange", "dlx")
                .withArgument("x-dead-letter-routing-key", "dlx.almaty_orders")
                .build();
    }

    @Bean
    public Queue deadLetterQueue() {
        return QueueBuilder.durable("almaty_orders_queue.dlq").build();
    }

    @Bean
    public TopicExchange orderExchange() {
        return ExchangeBuilder.topicExchange("order-topic-exchange").durable(true).build();
    }

    @Bean
    public TopicExchange deadLetterExchange() {
        return ExchangeBuilder.topicExchange("dlx").durable(true).build();
    }

    @Bean
    public Binding almatyQueueBinding(Queue almatyOrdersQueue, TopicExchange orderExchange) {
        return BindingBuilder
                .bind(almatyOrdersQueue)
                .to(orderExchange)
                .with("order.#");
    }

    @Bean
    public Binding dlqBinding(Queue deadLetterQueue, TopicExchange deadLetterExchange) {
        return BindingBuilder
                .bind(deadLetterQueue)
                .to(deadLetterExchange)
                .with("dlx.almaty_orders");
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        Jackson2JsonMessageConverter messageConverter = new Jackson2JsonMessageConverter();
        messageConverter.setClassMapper(classMapper());
        return messageConverter;
    }

    @Bean
    public DefaultClassMapper classMapper() {
        DefaultClassMapper classMapper = new DefaultClassMapper();
        Map<String, Class<?>> idClassMapping = new HashMap<>();
        idClassMapping.put("com.example.rabbit.rabbit_receiver.dto.OrderDTO", OrderDTO.class);
        classMapper.setIdClassMapping(idClassMapping);
        classMapper.setDefaultType(OrderDTO.class);
        return classMapper;
    }

    @Bean
    public Queue almatyQueue() {
        return new Queue("order.almaty");
    }

    @Bean
    public Queue astanaQueue() {
        return new Queue("order.astana");
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange("order-topic-exchange");
    }

    @Bean
    public Binding almatyBinding(Queue almatyQueue, TopicExchange topicExchange) {
        return new Binding("order.almaty", Binding.DestinationType.QUEUE, topicExchange.getName(), "order.almaty", null);
    }

    @Bean
    public Binding astanaBinding(Queue astanaQueue, TopicExchange topicExchange) {
        return new Binding("order.astana", Binding.DestinationType.QUEUE, topicExchange.getName(), "order.astana", null);
    }

}
