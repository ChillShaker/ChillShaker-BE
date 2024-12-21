package com.ducnt.chillshaker.service.implement;

import com.ducnt.chillshaker.dto.request.AccountCreationRequest;
import com.ducnt.chillshaker.dto.request.AccountUpdationRequest;
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
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Stream;

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

    public AccountResponse getAccountById(Long id) {
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

    public AccountResponse createAccount(AccountCreationRequest request) {
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
    }

    public AccountResponse updateAccount(Long id, AccountUpdationRequest request) throws NoSuchAlgorithmException {
        Account account = accountRepository.findById(id).orElseThrow(() -> new NotFoundException("Account not found"));
        modelMapper.map(request, account);

        account.setPassword(passwordEncoder.encode(request.getPassword()));

        if(!request.getRoles().isEmpty()) {
            List<Role> roles = roleRepository.findAllByName(request.getRoles());

            account.setRoles(roles);
        }
        
        accountRepository.save(account);
        return modelMapper.map(account, AccountResponse.class);
    }

    public boolean deleteAccount(Long id) throws Exception {
        try {
            Account account = accountRepository.findById(id).orElseThrow(() -> new NotFoundException("Account not found"));
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
