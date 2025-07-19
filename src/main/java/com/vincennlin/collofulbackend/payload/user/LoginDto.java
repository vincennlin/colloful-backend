package com.vincennlin.collofulbackend.payload.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        name = "LoginDto",
        description = "登入的 Data Transfer Object"
)
public class LoginDto {

    @Schema(
            name = "username_or_email",
            description = "用戶帳號名稱或電子郵件",
            example = "user"
    )
    @JsonProperty("username_or_email")
    @NotBlank(message = "Username or email is required")
    private String usernameOrEmail;

    @Schema(
            name = "password",
            description = "用戶密碼，至少 4 個字元",
            example = "password"
    )
    @NotBlank(message = "Password is required")
    @Size(min = 4, message = "Password must be at least 4 characters long")
    private String password;
}
