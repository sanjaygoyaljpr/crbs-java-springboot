package com.sanjaygoyaljpr.service;

import java.util.List;
import java.util.Optional;

import com.sanjaygoyaljpr.persistence.model.PasswordResetToken;
import com.sanjaygoyaljpr.persistence.model.User;
import com.sanjaygoyaljpr.persistence.model.VerificationToken;
import com.sanjaygoyaljpr.web.dto.UserDto;
import com.sanjaygoyaljpr.web.error.UserAlreadyExistException;

public interface IUserService {

	User registerNewUserAccount(UserDto accountDto) throws UserAlreadyExistException;

	User getUser(String verificationToken);

	void saveRegisteredUser(User user);

	void deleteUser(User user);

	void createVerificationTokenForUser(User user, String token);

	VerificationToken getVerificationToken(String VerificationToken);

	VerificationToken generateNewVerificationToken(String token);

	void createPasswordResetTokenForUser(User user, String token);

	User findUserByEmail(String email);

	PasswordResetToken getPasswordResetToken(String token);

	User getUserByPasswordResetToken(String token);

	Optional<User> getUserByID(long id);

	void changeUserPassword(User user, String password);

	boolean checkIfValidOldPassword(User user, String password);

	String validateVerificationToken(String token);

	List<String> getUsersFromSessionRegistry();

	User findOne(Long userId);

}
