package com.vaneezy.TestTask.Controllers.Registration;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SuccessPage {

    @GetMapping("/success")
    public String getSuccessPage() {
        return "success";
    }
}
