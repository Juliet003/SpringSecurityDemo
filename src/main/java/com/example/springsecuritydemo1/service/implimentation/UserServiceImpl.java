package com.example.springsecuritydemo1.service.implimentation;

import com.example.springsecuritydemo1.dto.request.LoginRequest;
import com.example.springsecuritydemo1.dto.request.RegistrationRequest;
import com.example.springsecuritydemo1.dto.response.ApiResponse;
import com.example.springsecuritydemo1.dto.response.LoginResponse;
import com.example.springsecuritydemo1.entity.User;
import com.example.springsecuritydemo1.exception.CustomException;
import com.example.springsecuritydemo1.repository.UserRepository;
import com.example.springsecuritydemo1.service.UserService;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@Builder
public class UserServiceImpl implements UserService {
    private final AuthenticationProvider authenticationProvider;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public ApiResponse<LoginResponse> userLogin(LoginRequest request) {


        return null;
    }

    @Override
    public ApiResponse<String> registerUser(RegistrationRequest request) {
        log.info("User login request");
        Boolean doesUserExist= userRepository.existsByEmail(request.getEmail());
        if (doesUserExist){
            throw new CustomException("Account already exist");
        }
        User newUser = User.builder()
                .email(request.getEmail())
                .fullName(request.getFullName())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        userRepository.save(newUser);
        return ApiResponse.<String>builder()
                .message("Account created")
                .build();
    }


}
