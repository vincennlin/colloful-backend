package com.vincennlin.collofulbackend.payload.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(
        name = "LoginResponse",
        description = "登入回應的 Data Transfer Object"
)
public class LoginResponse {

    @Schema(
            name = "message",
            description = "登入回應訊息",
            example = "User logged in successfully!"
    )
    private String message;

    @Schema(
            name = "access_token",
            description = "身份驗證的 JWT Token，之後的 API 請求都需要在 Header 加入 Authorization: Bearer ${access_token} 來進行驗證",
            example = "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJ1c2VyIiwiaWF0IjoxNzIwMzIxNTY5LCJleHAiOjE3MjA5MjYzNjl9.0urLX8bsnPe55Df1KXu4ZP4tn53WUWQZTwcO3Z36S2xVl5cwg8R8HXYfd5-8nzEP"
    )
    @JsonProperty(
            value = "access_token"
    )
    private String accessToken;

    @Schema(
            name = "token_type",
            description = "Token 的類型，固定回傳 Bearer",
            example = "Bearer"
    )
    @JsonProperty(
            value = "token_type"
    )
    private String tokenType = "Bearer";
}
