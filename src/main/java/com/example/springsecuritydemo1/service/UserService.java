package com.example.springsecuritydemo1.service;

import com.example.springsecuritydemo1.dto.request.LoginRequest;
import com.example.springsecuritydemo1.dto.request.RegistrationRequest;
import com.example.springsecuritydemo1.dto.response.ApiResponse;
import com.example.springsecuritydemo1.dto.response.LoginResponse;
import org.hibernate.validator.internal.constraintvalidators.bv.time.past.AbstractPastInstantBasedValidator;

public interface UserService {

    ApiResponse<LoginResponse> userLogin(LoginRequest request);

    ApiResponse<String>registerUser(RegistrationRequest request);
}
