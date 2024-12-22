package com.ducnt.chillshaker.controller;

import com.ducnt.chillshaker.dto.request.account.AccountCreationRequest;
import com.ducnt.chillshaker.dto.request.account.AccountUpdationRequest;
import com.ducnt.chillshaker.dto.response.account.AccountResponse;
import com.ducnt.chillshaker.dto.response.common.ApiResponse;
import com.ducnt.chillshaker.service.implement.AccountService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.UUID;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/accounts")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountController {
    AccountService accountService;

    @GetMapping("/{id}")
    public ApiResponse getAccountById(@PathVariable("id") UUID id) {
        AccountResponse accountResponse = accountService.getAccountById(id);
        return ApiResponse.builder()
                .message("Data loaded successfully")
                .data(accountResponse)
                .build();
    }

    @GetMapping
    public ApiResponse getAllAccount() {
        List<AccountResponse> accountResponses = accountService.getAllAccounts();
        return ApiResponse.builder()
                .message("Data loaded successfully")
                .data(accountResponses)
                .build();
    }

    @GetMapping("/myInfo")
    public ApiResponse getMyInfo() {
        AccountResponse accountResponses = accountService.getMyInfo();
        return ApiResponse.builder()
                .message("Data loaded successfully")
                .data(accountResponses)
                .build();
    }

    @PostMapping
    public ApiResponse createAccount(@RequestBody @Valid AccountCreationRequest request) throws Exception {
        AccountResponse accountResponse = accountService.createAccount(request);
        return ApiResponse.builder()
                .code(HttpStatus.CREATED.value())
                .message("Created account successfully")
                .data(accountResponse).build();
    }

    @PutMapping("/{id}")
    public ApiResponse updateAccount(@PathVariable("id") UUID id, @RequestBody @Valid AccountUpdationRequest request) throws NoSuchAlgorithmException {
        AccountResponse accountResponse = accountService.updateAccount(id, request);
        return ApiResponse.builder()
                .message("Updated account successfully")
                .data(accountResponse)
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse deleteAccount(@PathVariable("id") UUID id) throws Exception {
        boolean result = accountService.deleteAccount(id);
        if (result) {
            return ApiResponse.builder().message("Deleted account successfully").build();
        } else {
            return ApiResponse.builder().message("Failed to delete account successfully").build();
        }
    }
}
