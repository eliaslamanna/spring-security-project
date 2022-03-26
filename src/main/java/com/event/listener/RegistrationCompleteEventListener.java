package com.event.listener;

import com.event.RegistrationCompleteEvent;
import com.model.User;
import com.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {

    private final IUserService userService;

    @Autowired
    public RegistrationCompleteEventListener(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        //Crea el token de verificacion para el user con un link
        User user = event.getUser();
        String token = UUID.randomUUID().toString(); //creo token que comparo con el de la base a ver si es el correcto
        userService.saveVerificationTokenForUser(token,user);

        //Se manda el mail al user
        String url = event.getApplicationUrl() + "/verifyRegistration?token=" + token;

        //Aca se mandaria el mail de verificacion (ahora esta mockeado)
        //Se muestra en la consola, si el usuario clickea ahi entonces verifica el mail
        log.info("Click the link to verify your account: {}", url);
    }

}
