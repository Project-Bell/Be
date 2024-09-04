package domain.bell.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BellController {
    @GetMapping("")
    public String hello() {
        return "Hello World";
    }

}
