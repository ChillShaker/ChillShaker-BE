package com.ducnt.chillshaker.service.interfaces;

import com.ducnt.chillshaker.dto.request.account.AccountCreationRequest;
import com.ducnt.chillshaker.dto.request.account.AccountUpdateRequest;
import com.ducnt.chillshaker.dto.request.account.ProfileUpdateRequest;
import com.ducnt.chillshaker.dto.response.account.AccountResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface IAccountService {
    @PreAuthorize("hasRole('ADMIN')")
    List<AccountResponse> getAllAccounts();

    @PreAuthorize("hasRole('ADMIN')")
    AccountResponse getAccountById(UUID id);

    AccountResponse getMyInfo();

    AccountResponse updateMyInfo(ProfileUpdateRequest request);

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    AccountResponse createAccount(AccountCreationRequest request) throws Exception;

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    AccountResponse updateAccount(UUID id, AccountUpdateRequest request) throws Exception;

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    boolean deleteAccount(UUID id) throws Exception;
}
