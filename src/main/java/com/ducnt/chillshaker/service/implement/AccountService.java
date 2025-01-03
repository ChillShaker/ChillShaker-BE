package com.ducnt.chillshaker.service.implement;

import com.ducnt.chillshaker.dto.request.account.AccountCreationRequest;
import com.ducnt.chillshaker.dto.request.account.AccountUpdationRequest;
import com.ducnt.chillshaker.dto.response.account.AccountResponse;
import com.ducnt.chillshaker.exception.CustomException;
import com.ducnt.chillshaker.exception.ErrorResponse;
import com.ducnt.chillshaker.exception.NotFoundException;
import com.ducnt.chillshaker.model.Account;
import com.ducnt.chillshaker.model.Role;
import com.ducnt.chillshaker.repository.AccountRepository;
import com.ducnt.chillshaker.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AccountService {
    AccountRepository accountRepository;
    RoleRepository roleRepository;
    ModelMapper modelMapper;
    PasswordEncoder passwordEncoder;

    @PreAuthorize("hasRole('ADMIN')")
    public List<AccountResponse> getAllAccounts() {
        return accountRepository.findAll()
                .stream()
                .map(account -> modelMapper.map(account, AccountResponse.class))
                .toList();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public AccountResponse getAccountById(UUID id) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new NotFoundException("Account not found"));
        return modelMapper.map(account, AccountResponse.class);
    }

    public AccountResponse getMyInfo() {
        SecurityContext contextHolder = SecurityContextHolder.getContext();
        String email = contextHolder.getAuthentication().getName();

        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Account not found"));

        return modelMapper.map(account, AccountResponse.class);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public AccountResponse createAccount(AccountCreationRequest request) throws Exception {
        try {
            Role role = Role.builder().name(Role.RoleName.USER.name()).build();
            HashSet<Role> roles = new HashSet<>();
            roles.add(role);

            if (accountRepository.existsByEmail(request.getEmail()))
                throw new CustomException(ErrorResponse.DATA_EXISTED);

            Account account = modelMapper.map(request, Account.class);
            account.setPassword(passwordEncoder.encode(request.getPassword()));
            account.setRoles(roles);

            accountRepository.save(account);
            return modelMapper.map(account, AccountResponse.class);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public AccountResponse updateAccount(UUID id, AccountUpdationRequest request) throws Exception {
        try {
            Account account = accountRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Account not found"));
            modelMapper.map(request, account);

            account.setPassword(passwordEncoder.encode(request.getPassword()));

            accountRepository.save(account);
            return modelMapper.map(account, AccountResponse.class);
        } catch (NotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }
        catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public boolean deleteAccount(UUID id) throws Exception {
        try {
            Account account = accountRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Account not found"));
            accountRepository.delete(account);
            return true;
        }
        catch (NotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }
        catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

}
