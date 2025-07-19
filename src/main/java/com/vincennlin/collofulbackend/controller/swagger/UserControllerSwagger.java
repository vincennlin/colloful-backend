package com.vincennlin.collofulbackend.controller.swagger;

import com.vincennlin.collofulbackend.payload.user.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


@Tag(
        name = "User Controller",
        description = "用戶相關的 API"
)
public interface UserControllerSwagger {

    @Operation(
            summary = "檢查用戶服務狀態",
            description = "檢查用戶服務是否正常運作"
    )
    @ApiResponse(
            responseCode = "200",
            description = "用戶服務正常運作",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = "User Service is up and running on port 61827")
            )
    )
    @SecurityRequirement(name = "Bear Authentication")
    ResponseEntity<String> status();

    @Operation(
            summary = "註冊用戶",
            description = "註冊一個新用戶，注意 username 和 email 都不可和其他用戶重複，password 至少 4 個字元"
    )
    @ApiResponse(
            responseCode = "201",
            description = "註冊成功",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = """
                            {
                                "message": "User registered successfully!",
                                "account_info": {
                                    "id": 1,
                                    "name": "user",
                                    "username": "user",
                                    "email": "user@gmail.com",
                                    "date_created": "2024-07-21T21:58:12.642225",
                                    "last_updated": "2024-07-21T21:58:12.642802"
                                }
                            }
                            """)
            )
    )
    ResponseEntity<RegisterResponse> register(@Valid @RequestBody
                                                     @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                             content = @Content(
                                                                     mediaType = "application/json",
                                                                     examples = @ExampleObject(value = """
                                                                                 {
                                                                                     "name": "user",
                                                                                     "username": "user",
                                                                                     "email": "user@gmail.com",
                                                                                     "password": "user"
                                                                                 }
                                                                                 """)
                                                             )
                                                     ) RegisterDto registerDto);

    @Operation(
            summary = "登入",
            description = "登入用戶"
    )
    @ApiResponse(
            responseCode = "200",
            description = "登入成功，Response Header 的 'access_token' 欄位會包含 JWT Token，'user_id' 會包含用戶的 id"
    )
    ResponseEntity<Void> login(@Valid @RequestBody
                                      @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                              content = @Content(
                                                      mediaType = "application/json",
                                                      examples = @ExampleObject(value = """
                                                                           {
                                                                               "username_or_email": "user",
                                                                               "password": "user"
                                                                           }
                                                                           """)
                                              )
                                      ) LoginDto loginDto);

    @Operation(
            summary = "取得用戶資訊",
            description = "根據用戶 id 取得用戶資訊"
    )
    @ApiResponse(
            responseCode = "200",
            description = "取得用戶資訊",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = """
                            {
                                "id": 1,
                                "name": "user",
                                "username": "user",
                                "email": "user@gmail.com",
                                "date_created": "2024-07-21T21:59:34.451019",
                                "last_updated": "2024-07-21T21:59:34.45108"
                            }
                            """
                    )
            )
    )
    @SecurityRequirement(name = "Bear Authentication")
    ResponseEntity<UserDto> getUserByUserId(@PathVariable("user_id") Long userId);

    @Operation(
            summary = "取得目前帳號資訊",
            description = "根據 JWT Token 取得目前登入的用戶資訊"
    )
    @ApiResponse(
            responseCode = "200",
            description = "取得目前帳號資訊",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = """
                            {
                                "id": 1,
                                "name": "user",
                                "username": "user",
                                "email": "user@gmail.com",
                                "date_created": "2024-07-21T21:59:34.451019",
                                "last_updated": "2024-07-21T21:59:34.45108"
                            }
                            """)
            )
    )
    @SecurityRequirement(name = "Bear Authentication")
    ResponseEntity<AccountInfoDto> getCurrentAccountInfo();

    @Operation(
            summary = "以 ADMIN 身份取得所有用戶",
            description = "取得所有用戶，只有 ADMIN 可以存取"
    )
    @ApiResponse(
            responseCode = "200",
            description = "取得所有用戶",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = """
                            [
                                 {
                                     "id": 1,
                                     "name": "admin",
                                     "username": "admin",
                                     "email": "admin@gmail.com",
                                     "date_created": "2024-07-18T13:39:10.829251",
                                     "last_updated": "2024-07-18T13:39:10.829308"
                                 },
                                 {
                                     "id": 2,
                                     "name": "user",
                                     "username": "user",
                                     "email": "user@gmail.com",
                                     "date_created": "2024-07-18T13:41:31.917832",
                                     "last_updated": "2024-07-18T22:59:26.437294"
                                 },
                                 {
                                     "id": 3,
                                     "name": "test",
                                     "username": "test",
                                     "email": "test@gmail.com",
                                     "date_created": "2024-07-19T10:15:32.789745",
                                     "last_updated": "2024-07-19T10:15:32.790476"
                                 }
                            ]
                            """)
            )
    )
    @SecurityRequirement(name = "Bear Authentication")
    ResponseEntity<List<AccountInfoDto>> getAllUsers();

//    @Operation(
//            summary = "[NEW] 取得目前帳號頭貼",
//            description = "取得目前帳號頭貼"
//    )
//    @ApiResponse(
//            responseCode = "200",
//            description = "取得目前帳號頭貼",
//            content = @Content(
//                    mediaType = "image/jpeg",
//                    examples = @ExampleObject(value = "照片")
//            )
//    )
//    @SecurityRequirement(name = "Bear Authentication")
//    ResponseEntity<byte[]> getProfilePicture();

    @Operation(
            summary = "更新目前帳號資訊",
            description = "更新目前帳號資訊"
    )
    @ApiResponse(
            responseCode = "200",
            description = "只要有更新 username, email 或 password 就需要重新登入",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = """
                            {
                                "message": "User updated successfully! Please login again.",
                                "account_info": {
                                    "id": 1,
                                    "name": "user_updated",
                                    "username": "user_updated",
                                    "email": "user_updated@gmail.com",
                                    "date_created": "2024-07-21T21:59:34.451019",
                                    "last_updated": "2024-07-21T21:59:34.45108"
                                }
                            }
                            """)
            )
    )
    @SecurityRequirement(name = "Bear Authentication")
    ResponseEntity<UpdateAccountInfoResponse> updateAccountInfo(@Valid @RequestBody
                                                                       @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                                               content = @Content(
                                                                                       mediaType = "application/json",
                                                                                       examples = @ExampleObject(value = """
                                                                                                   {
                                                                                                       "name": "user_updated",
                                                                                                       "username": "user_updated",
                                                                                                       "email": "user_updated@gmail.com",
                                                                                                       "password": "user_updated"
                                                                                                   }
                                                                                                   """)
                                                                               )
                                                                       ) AccountInfoDto accountInfoDto);

    @Operation(
            summary = "更新目前帳號密碼",
            description = "更新目前帳號密碼"
    )
    @ApiResponse(
            responseCode = "200",
            description = "更新目前帳號密碼",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = """
                            {
                                "message": "Password updated successfully! Please login again.",
                                "account_info": {
                                    "id": 2,
                                    "name": "user",
                                    "username": "user",
                                    "email": "user@gmail.com",
                                    "date_created": "2024-07-24T11:58:29.924037",
                                    "last_updated": "2024-08-13T18:03:30.822299"
                                }
                            }
                            """)
            )
    )
    ResponseEntity<UpdateAccountInfoResponse> changePassword(@Valid @RequestBody
                                                             @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                                     content = @Content(
                                                                             mediaType = "application/json",
                                                                             examples = @ExampleObject(value = """
                                                                                     {
                                                                                         "old_password": "user",
                                                                                         "new_password": "new_user_password"
                                                                                     }
                                                                                     """)
                                                                     )
                                                             ) ChangePasswordRequest request);

//    @Operation(
//            summary = "[NEW] 更新個人頭貼",
//            description = "更新個人頭貼。必須指定 mediaType 為 multipart/form-data。Request body 必須是照片，其中 key 為 'file'，value 為該照片"
//    )
//    @ApiResponse(
//            responseCode = "200",
//            description = "更新個人頭貼",
//            content = @Content(
//                    mediaType = "application/json",
//                    examples = @ExampleObject(value = """
//                            {
//                                "message": "Profile picture updated successfully!",
//                                "account_info": {
//                                    "id": 2,
//                                    "name": "user",
//                                    "username": "user",
//                                    "email": "user@gmail.com",
//                                    "date_created": "2024-07-24T11:58:29.924037",
//                                    "last_updated": "2024-08-16T21:51:18.499018"
//                                }
//                            }
//                    """)
//            )
//    )
//    @SecurityRequirement(name = "Bear Authentication")
//    ResponseEntity<UpdateAccountInfoResponse> updateProfilePicture(@RequestPart("file")
//                                                                   @io.swagger.v3.oas.annotations.parameters.RequestBody(
//                                                                           description = "照片檔案",
//                                                                           required = true,
//                                                                           content = @Content(
//                                                                                   mediaType = "multipart/form-data",
//                                                                                   examples = @ExampleObject(value = """
//                                                                    {
//                                                                        "file": "照片"
//                                                                    }
//                                                                    """)
//                                                                           )
//                                                                   ) MultipartFile profilePicture);
}
