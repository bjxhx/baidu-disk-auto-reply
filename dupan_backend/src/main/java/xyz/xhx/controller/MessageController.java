package xyz.xhx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import xyz.xhx.entity.AutoKeyCommons;
import xyz.xhx.entity.Result;
import xyz.xhx.service.DupanService;

import java.util.List;

@Controller
@RequestMapping("/msg")
public class MessageController {

    @Autowired
    private DupanService dupanService;


}
