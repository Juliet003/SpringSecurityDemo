package com.example.springsecuritydemo1.service.implimentation;

import com.example.springsecuritydemo1.dto.request.LoginRequest;
import com.example.springsecuritydemo1.dto.request.RegistrationRequest;
import com.example.springsecuritydemo1.dto.response.ApiResponse;
import com.example.springsecuritydemo1.dto.response.LoginResponse;
import com.example.springsecuritydemo1.entity.User;
import com.example.springsecuritydemo1.enums.Roles;
import com.example.springsecuritydemo1.exception.CustomException;
import com.example.springsecuritydemo1.repository.UserRepository;
import com.example.springsecuritydemo1.security.JwtService;
import com.example.springsecuritydemo1.service.UserService;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.AbstractMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
@RequiredArgsConstructor
@Builder
public class UserServiceImpl implements UserService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public ApiResponse<String> registerUser(RegistrationRequest request) {
        log.info("User login request");
        Boolean doesUserExist = userRepository.existsByEmail(request.getEmail());
        if (doesUserExist) {
            throw new CustomException("Account already exist");
        }
        User newUser = User.builder()
                .email(request.getEmail())
                .fullName(request.getFullName())
                .password(passwordEncoder.encode(request.getPassword()))
                .confirmPassword(passwordEncoder.encode(request.getConfirmPassword()))
                .roles(Roles.USER)
                .IsVerified(true)
                .build();
        userRepository.save(newUser);
        return ApiResponse.<String>builder()
                .message("Account created")
                .status("00")
                .httpStatus(HttpStatus.CREATED)
                .build();
    }

    @Override
    public ApiResponse<LoginResponse> userLogin( LoginRequest request) {
        log.info("Login Request");

        Authentication authenticationUser;
        try {
            authenticationUser = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            log.info("Authenticated the user using the AuthenticationManager");
        } catch (DisabledException e) {
            return Stream.of(
                    new AbstractMap.SimpleEntry<>("message", "Disabled exception occurred"),
                    new AbstractMap.SimpleEntry<>("status", "failure"),
                    new AbstractMap.SimpleEntry<>("httpStatus", HttpStatus.BAD_REQUEST)).collect(Collectors.collectingAndThen(
                    Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue),
                    map -> new ApiResponse<>((Map<String, String>) map)
            ));
        } catch (BadCredentialsException e) {
            throw new CustomException("Invalid email or password", HttpStatus.BAD_REQUEST);
        }

        log.info("tell securityContext that this is in the context");
        SecurityContextHolder.getContext().setAuthentication(authenticationUser);

        log.info("Retrieve the user from repository");
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() ->
                new CustomException("User not found", HttpStatus.BAD_REQUEST));

        user.setLastLogin(LocalDateTime.now());
        log.info("Last login date updated");

        User user1 = userRepository.save(user);
        log.info("User saved back to database");

        //Generate and send token
        String tokenGenerated = "Bearer" + jwtService.generateToken(authenticationUser, user1.getRoles());
        log.info("Jwt token generated for the User " + tokenGenerated);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(tokenGenerated);

        return new ApiResponse<>("Successful", "00", HttpStatus.OK, loginResponse);
    }
}
