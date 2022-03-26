package com.service;

import com.model.User;
import com.model.VerificationToken;
import com.model.dto.UserDTO;

public interface IUserService {
    User registerUser(UserDTO user);
    void saveVerificationTokenForUser(String token, User user);
    String validateVerificationToken(String token);
    VerificationToken generateNewVerificationToken(String oldToken);
}
