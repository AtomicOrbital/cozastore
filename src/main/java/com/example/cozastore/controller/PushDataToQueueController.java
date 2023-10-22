package com.example.cozastore.controller;

import com.example.cozastore.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/push")
public class PushDataToQueueController {

    @Autowired
    private RabbitTemplate rabbitTemplate;


    @PostMapping("/queue")
    public ResponseEntity<?> pushToQueue(@RequestParam String data){
        rabbitTemplate.convertAndSend("exstorage22","/routeStorage22", data);
        return new ResponseEntity<>("Hello", HttpStatus.OK);
    }
}
