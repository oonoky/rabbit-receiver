package com.example.rabbit.rabbit_receiver.config;

import com.example.rabbit.rabbit_receiver.dto.Message;
import com.example.rabbit.rabbit_receiver.dto.OrderDTO;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitConfig {

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
        idClassMapping.put("com.example.rabbit.rabbit_receiver.dto.Message", Message.class);
        classMapper.setIdClassMapping(idClassMapping);
        return classMapper;
    }
}
