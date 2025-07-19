package com.vincennlin.collofulbackend.controller;

import com.vincennlin.collofulbackend.payload.user.*;
import com.vincennlin.collofulbackend.service.user.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class UserController implements UserControllerSwagger{

    private Environment env;

    private UserService userService;

    @GetMapping("/users/status/check")
    public ResponseEntity<String> status() {
        return new ResponseEntity<>("User Service is up and running on port: " + env.getProperty("local.server.port")
                + " with token " + env.getProperty("token.secret"), HttpStatus.OK);
    }

    @PostMapping(value = {"/auth/register"})
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterDto registerDto) {

        RegisterResponse registerResponse = userService.register(registerDto);
        return new ResponseEntity<>(registerResponse, HttpStatus.CREATED);
    }

    @CrossOrigin(
            exposedHeaders = {
                    "Access-Control-Allow-Origin",
                    "Token-Type",
                    "Access-Token",
                    "User-Id"
            },
            origins = {
                    "http://localhost:3000"
            }
    )
    @PostMapping(value = {"/auth/login"})
    public ResponseEntity<Void> login(@Valid @RequestBody LoginDto loginDto) {
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping("/users/{user_id}")
    public ResponseEntity<UserDto> getUserByUserId(@PathVariable("user_id") Long userId) {

        UserDto userDto = userService.getUserByUserId(userId);

        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('READ')")
    @GetMapping("/users/info")
    public ResponseEntity<AccountInfoDto> getCurrentAccountInfo() {

        AccountInfoDto accountInfo = userService.getCurrentAccountInfo();

        return new ResponseEntity<>(accountInfo, HttpStatus.OK);
    }

//    @PreAuthorize("hasAuthority('READ')")
//    @GetMapping("/users/profile-picture")
//    public ResponseEntity<byte[]> getProfilePicture() {
//
//        byte[] profilePicture = userService.getProfilePicture();
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.IMAGE_JPEG);
//        headers.setContentLength(profilePicture.length);
//
//        return new ResponseEntity<>(profilePicture, headers, HttpStatus.OK);
//    }

    @PreAuthorize("hasAuthority('ADVANCED')")
    @GetMapping("/users/all")
    public ResponseEntity<List<AccountInfoDto>> getAllUsers() {

        List<AccountInfoDto> accountInfos = userService.getAllUsers();

        return new ResponseEntity<>(accountInfos, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('UPDATE')")
    @PutMapping("/users/info")
    public ResponseEntity<UpdateAccountInfoResponse> updateAccountInfo(@Valid @RequestBody AccountInfoDto accountInfoDto) {

        UpdateAccountInfoResponse updateAccountInfoResponse = userService.updateAccountInfo(accountInfoDto);

        return new ResponseEntity<>(updateAccountInfoResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('UPDATE')")
    @PutMapping("/users/password")
    public ResponseEntity<UpdateAccountInfoResponse> changePassword(@Valid @RequestBody ChangePasswordRequest request) {

        UpdateAccountInfoResponse updateAccountInfoResponse = userService.changePassword(request);

        return new ResponseEntity<>(updateAccountInfoResponse, HttpStatus.OK);
    }

//    @PreAuthorize("hasAuthority('UPDATE')")
//    @PutMapping("/users/profile-picture")
//    public ResponseEntity<UpdateAccountInfoResponse> updateProfilePicture(@RequestPart("file") MultipartFile profilePicture) {
//
//        UpdateAccountInfoResponse updateAccountInfoResponse = userService.updateProfilePicture(profilePicture);
//
//        return new ResponseEntity<>(updateAccountInfoResponse, HttpStatus.OK);
//    }
}
