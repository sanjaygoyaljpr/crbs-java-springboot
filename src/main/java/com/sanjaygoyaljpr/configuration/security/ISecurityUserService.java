package com.sanjaygoyaljpr.configuration.security;

public interface ISecurityUserService {

    String validatePasswordResetToken(long id, String token);

}
