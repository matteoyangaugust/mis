package com.matt.controller;

import com.matt.bean.ResultBean;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController extends BaseController{
    @MessageMapping("/calcApp")
    @SendTo("/topic/showResult")
    public ResultBean add(String change) throws Exception {
        Thread.sleep(2000);
        return new ResultBean(true, "change");
    }
}
