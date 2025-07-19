package com.vincennlin.collofulbackend.service.user;

import com.vincennlin.collofulbackend.payload.user.*;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    RegisterResponse register(RegisterDto registerDto);

    UserDto getUserDetailsByUsername(String username);

    UserDto getUserByUserId(Long userId);

    AccountInfoDto getCurrentAccountInfo();

//    byte[] getProfilePicture();

    List<AccountInfoDto> getAllUsers();

    UpdateAccountInfoResponse updateAccountInfo(AccountInfoDto accountInfoDto);

    UpdateAccountInfoResponse changePassword(ChangePasswordRequest request);

//    UpdateAccountInfoResponse updateProfilePicture(MultipartFile profilePicture);
}
