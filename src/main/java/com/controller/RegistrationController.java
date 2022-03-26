package com.controller;

import com.event.RegistrationCompleteEvent;
import com.model.User;
import com.model.VerificationToken;
import com.model.dto.UserDTO;
import com.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
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

    @GetMapping("/resendVerifyToken")
    public String resendVerificationToken(@RequestParam("token") String oldToken, HttpServletRequest request) { //Recibe el viejo token
        VerificationToken verificationToken = userService.generateNewVerificationToken(oldToken);
        User user = verificationToken.getUser();
        resendVerificationTokenMail(user,applicationUrl(request),verificationToken);
        return "Verification Link Sent";
    }

    //Por ahora lo mockea
    private String applicationUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

    private void resendVerificationTokenMail(User user, String applicationUrl, VerificationToken verificationToken) {
        String url = applicationUrl + "/verifyRegistration?token=" + verificationToken.getToken();

        //Aca se mandaria el mail de verificacion (ahora esta mockeado)
        //Se muestra en la consola, si el usuario clickea ahi entonces verifica el mail
        log.info("Click the link to verify your account: {}", url);
    }
}
