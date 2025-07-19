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
public class ChangePasswordRequest {

    @Schema(
            name = "oldPassword",
            description = "用戶舊密碼",
            example = "oldPassword"
    )
    @JsonProperty(
            value = "old_password"
    )
    private String oldPassword;

    @Schema(
            name = "newPassword",
            description = "用戶新密碼",
            example = "newPassword"
    )
    @JsonProperty(
            value = "new_password"
    )
    private String newPassword;
}
