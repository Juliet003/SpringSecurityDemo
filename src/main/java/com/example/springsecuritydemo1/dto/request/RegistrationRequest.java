package com.example.springsecuritydemo1.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistrationRequest {
    @NotBlank(message = "Full Name is required")
    private String fullName;

    @NotBlank(message = "Email is required")
    @Email(message = "Provide a valid email")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(max=15,min=6,message = "Password should be greater than 15 not less than 8")
    private String password;

    @NotBlank(message = "Confirmed Password is required")
    @Size(max=15,min=6,message = "Confirmed Password should be greater than 15 not less than 8")
    private String confirmPassword;

}
