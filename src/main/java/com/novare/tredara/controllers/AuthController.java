package com.novare.tredara.controllers;

import com.novare.tredara.models.ERole;
import com.novare.tredara.models.User;
import com.novare.tredara.payloads.InfoResponse;
import com.novare.tredara.payloads.LoginRequest;
import com.novare.tredara.payloads.SignupRequest;
import com.novare.tredara.payloads.UserInfoResponse;
import com.novare.tredara.security.jwt.JwtTokenUtil;
import com.novare.tredara.security.userdetails.UserDetailsImpl;
import com.novare.tredara.services.LogService;
import com.novare.tredara.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenUtil jwtUtils;
    @Autowired
    private UserService userService;

    @Autowired
    private LogService logService;

    @PostMapping("/login/")
    public ResponseEntity<?> authenticateUser(@RequestBody @Valid LoginRequest loginRequest) {
        try {
            Authentication
                    authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
//            System.out.println("email : "+loginRequest.getEmail());
            String jwt = jwtUtils.generateJwtToken(userDetails);
//            System.out.println("jwt : "+jwt);

            String role = userDetails.getAuthorities().iterator().next().getAuthority();
            UserInfoResponse response = new UserInfoResponse(userDetails.getId(),
                    userDetails.getUsername(),
                    userDetails.getFullName(),
                    role);
            //There is just one role in this project so add it to the response
            int userType = switch (role) {
                case "ROLE_ADMIN" -> 1;
                case "ROLE_CUSTOMER" -> 2;
                default -> throw new IllegalArgumentException("Unexpected value: " + role);
            };
            response.setType(userType);
            // Log successful login
            logService.logSuccessfulLogin(loginRequest.getEmail());

            return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION,jwt).body(response);
        } catch (AuthenticationException e) {
            // Authentication failed Log successful login
            logService.logFailedLogin(loginRequest.getEmail());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED) // 401 Unauthorized
                    .body("Authentication failed: " + e.getMessage());
        }
    }

    @PostMapping("/signup/")
    public ResponseEntity<?> registerUser(@RequestBody @Valid SignupRequest signUpRequest) {
        String password = signUpRequest.getPassword();
        if (!userService.isPasswordValid(password)) {
            return ResponseEntity.badRequest().body(new InfoResponse("Error: Password does not meet the criteria. " +
                    "It must be at least 8 characters and contain uppercase and lowercase letters and special symbols"));
        }
        if (userService.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new InfoResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getName(),
                signUpRequest.getEmail(),
                password);
        user.setRole(ERole.ROLE_CUSTOMER);
        final User createUser = userService.saveUser(user);
        UserDetailsImpl userDetails=UserDetailsImpl.build(createUser);
        String jwt = jwtUtils.generateJwtToken(userDetails);
        UserInfoResponse response = new UserInfoResponse(createUser.getId(),
                createUser.getEmail(),
                createUser.getFullName(),
                createUser.getRole().name());
        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION,jwt).body(response);
    }

    @PostMapping("/logout/")
    public ResponseEntity<?> logoutUser() {
        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, "")
                .body(new InfoResponse("You've been signed out!"));
    }
}

