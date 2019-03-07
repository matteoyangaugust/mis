package com.matt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/line/")
public class LineController extends BaseController{

    private static final String MESSAGING_VIEW = "line/messaging";

//    @RequestMapping(value="messaging.do")
//    public String messaging(Model model){
//        log.info("123");
//        return toView(MESSAGING_VIEW, model);
//    }
}
