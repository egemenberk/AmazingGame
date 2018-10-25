package com.ceng453.Server;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @RequestMapping("/")
    public ResponseGenerator greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new ResponseGenerator(name);
    }
}
