package com.controller;

import com.event.RegistrationCompleteEvent;
import com.model.User;
import com.model.dto.UserDTO;
import com.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/register")
@RestController
public class RegistrationController {

    private final IUserService userService;
    private final ApplicationEventPublisher publisher;

    @Autowired
    public RegistrationController(IUserService userService, ApplicationEventPublisher publisher) {
        this.userService = userService;
        this.publisher = publisher;
    }

    @PostMapping
    public String registerUser(@RequestBody UserDTO userDTO, final HttpServletRequest request) {
        User user = userService.registerUser(userDTO);
        //creo evento enviando token en forma de email y ese email se usa para verificar al usuario
        publisher.publishEvent(new RegistrationCompleteEvent(user,applicationUrl(request)));
        return "Success";
    }

    //Por ahora lo mockea
    private String applicationUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
}
