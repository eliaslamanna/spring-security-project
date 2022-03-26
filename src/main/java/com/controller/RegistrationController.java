package com.controller;

import com.event.RegistrationCompleteEvent;
import com.model.User;
import com.model.dto.UserDTO;
import com.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class RegistrationController {

    private final IUserService userService;
    private final ApplicationEventPublisher publisher;

    @Autowired
    public RegistrationController(IUserService userService, ApplicationEventPublisher publisher) {
        this.userService = userService;
        this.publisher = publisher;
    }

    @PostMapping("/register")
    public String registerUser(@RequestBody UserDTO userDTO, final HttpServletRequest request) {
        User user = userService.registerUser(userDTO);
        //creo evento enviando token en forma de email y ese email se usa para verificar al usuario
        publisher.publishEvent(new RegistrationCompleteEvent(user,applicationUrl(request)));
        return "Success";
    }

    @GetMapping("/verifyRegistration")
    public String verifyRegistration(@RequestParam("token") String token) {
        String result = userService.validateVerificationToken(token);
        if(result.equalsIgnoreCase("valid")) {
            return "User Verifies Successfully";
        } else {
            return "Bad User";
        }
    }

    //Por ahora lo mockea
    private String applicationUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
}
