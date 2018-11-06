package com.shuibeizi.gateway;

import com.shuibeizi.common.util.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Administrator
 * @description:
 * @date: Create in 2018/10/27 0027 下午 6:00
 * @modified:
 */
@Controller
@RestController
public class MainController {

//    @GetMapping("/test")
    public Result test(){
        return Result.getSuccessdResult();
    }
}
