package com.example.CUSHProjectAPI;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DelayController{
    @GetMapping("/delay/{sec}")
    public String delay(@PathVariable String sec) throws InterruptedException {
        Thread.sleep(Integer.parseInt(sec) * 1000);
        return "Done!";
    }
}
