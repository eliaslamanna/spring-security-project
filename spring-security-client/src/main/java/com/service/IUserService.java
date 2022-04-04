package com.service;

import com.model.User;
import com.model.VerificationToken;
import com.model.dto.UserDTO;

import java.util.Optional;

public interface IUserService {
    User registerUser(UserDTO user);
    void saveVerificationTokenForUser(String token, User user);
    String validateVerificationToken(String token);
    VerificationToken generateNewVerificationToken(String oldToken);
    User findUserByEmail(String email);
    void createPasswordResetTokenForUser(User user, String token);
    String validatePasswordResetToken(String token);
    Optional<User> getUserByPasswordResetToken(String token);
    void changePassword(User user, String newPassword);
    boolean oldPasswordIsValid(User user, String oldPassword);
}
