package com.vaneezy.TestTask.Controllers.Registration;

import com.vaneezy.TestTask.Entities.ApplicationUser.RegistrationRequest;
import com.vaneezy.TestTask.Services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService){
        this.userService = userService;
    }

    @RequestMapping("/registration")
    public String registration(Model model){
        model.addAttribute("userForm", new RegistrationRequest());
        return "registration";
    }

    @PostMapping("/registration")
    public String addNewUser(@ModelAttribute("userForm") @Validated RegistrationRequest registrationRequest,
                             BindingResult bindingResult,
                             Model model){
        if(bindingResult.hasErrors()) return "registration";
        boolean success = userService.registerUser(registrationRequest);
        if(!success) {
            model.addAttribute("usernameError", "User with this username already exists");
            return "registration";
        }
        return "redirect:/login";
    }
}
