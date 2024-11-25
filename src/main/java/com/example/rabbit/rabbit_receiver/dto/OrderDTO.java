package com.example.rabbit.rabbit_receiver.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO implements Serializable {

    private String restaurant;
    private String courier;
    private List<String> foods;
    private String region;
    private String status;


}
