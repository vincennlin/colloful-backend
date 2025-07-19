package com.vincennlin.collofulbackend.payload.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountInfoDto {

    @Schema(
            name = "id",
            description = "用戶 id",
            example = "1"
    )
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @Schema(
            name = "name",
            description = "用戶暱稱",
            example = "user"
    )
    private String name;

    @Schema(
            name = "username",
            description = "用戶帳號名稱",
            example = "user"
    )
    private String username;

    @Schema(
            name = "email",
            description = "用戶電子郵件",
            example = "user@gmail.com"
    )
    @Email(message = "Invalid email format")
    private String email;

    @Schema(
            name = "date_created",
            description = "帳號建立日期",
            example = "2021-08-01T00:00:00"
    )
    @JsonProperty(
            value = "date_created",
            access = JsonProperty.Access.READ_ONLY
    )
    @CreationTimestamp
    private LocalDateTime dateCreated;

    @Schema(
            name = "last_updated",
            description = "帳號最後更新日期",
            example = "2021-08-01T00:00:00"
    )
    @JsonProperty(
            value = "last_updated",
            access = JsonProperty.Access.READ_ONLY
    )
    @UpdateTimestamp
    private LocalDateTime lastUpdated;
}