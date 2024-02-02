package com.example.springsecuritydemo1.controller;

import com.example.springsecuritydemo1.dto.request.LoginRequest;
import com.example.springsecuritydemo1.dto.request.RegistrationRequest;
import com.example.springsecuritydemo1.dto.response.ApiResponse;
import com.example.springsecuritydemo1.dto.response.LoginResponse;
import com.example.springsecuritydemo1.exception.CustomException;
import com.example.springsecuritydemo1.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

 @PostMapping("/signup")
    public ResponseEntity<ApiResponse<String>> registerUser(@RequestBody @Valid RegistrationRequest request){
     log.info("register request");
   ApiResponse<String> response = userService.registerUser(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
 }
    public  static boolean isRequestValid(RegistrationRequest request){
     log.info("Validate register request");
     String password = request.getPassword();
     String confirmPassword = request.getConfirmPassword();
     if (Objects.equals(password,confirmPassword)){
         return true;
     }else {
         throw new CustomException("Password and confirm password must match",HttpStatus.BAD_REQUEST);
     }
    }
   @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login (@RequestBody LoginRequest loginRequest){
     ApiResponse<LoginResponse> response = userService.userLogin(loginRequest);
     return ResponseEntity.status(HttpStatus.OK).body(response);
   }
}
