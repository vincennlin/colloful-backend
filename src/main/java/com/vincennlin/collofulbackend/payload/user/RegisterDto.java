package com.vincennlin.collofulbackend.payload.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(
        name = "RegisterDto",
        description = "註冊的 Data Transfer Object"
)
public class RegisterDto {

    @Schema(
            name = "id",
            description = "新用戶暱稱",
            example = "user"
    )
    @NotBlank(message = "Name is required")
    private String name;

    @Schema(
            name = "username",
            description = "新用戶帳號名稱，不可和其他用戶重複",
            example = "user"
    )
    @NotBlank(message = "Username is required")
    private String username;

    @Schema(
            name = "email",
            description = "新用戶電子郵件，不可和其他用戶重複",
            example = "user@gmail.com"
    )
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @Schema(
            name = "password",
            description = "新用戶密碼，至少 4 個字元",
            example = "password"
    )
    @NotBlank(message = "Password is required")
    @Size(min = 4, message = "Password must be at least 4 characters long")
    private String password;
}
