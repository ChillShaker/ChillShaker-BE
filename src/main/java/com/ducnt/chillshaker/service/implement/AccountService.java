package com.ducnt.chillshaker.service.implement;

import com.ducnt.chillshaker.dto.request.account.AccountCreationRequest;
import com.ducnt.chillshaker.dto.request.account.AccountUpdateRequest;
import com.ducnt.chillshaker.dto.request.account.ProfileUpdateRequest;
import com.ducnt.chillshaker.dto.response.account.AccountResponse;
import com.ducnt.chillshaker.enums.RoleEnum;
import com.ducnt.chillshaker.exception.CustomException;
import com.ducnt.chillshaker.exception.ErrorResponse;
import com.ducnt.chillshaker.exception.NotFoundException;
import com.ducnt.chillshaker.model.Account;
import com.ducnt.chillshaker.model.Role;
import com.ducnt.chillshaker.repository.AccountRepository;
import com.ducnt.chillshaker.repository.RoleRepository;
import com.ducnt.chillshaker.service.thirdparty.CloudinaryService;
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

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
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
    CloudinaryService cloudinaryService;

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

    public AccountResponse updateMyInfo(ProfileUpdateRequest request) {
        try {
            SecurityContext contextHolder = SecurityContextHolder.getContext();
            String email = contextHolder.getAuthentication().getName();

            if(!Objects.equals(email, request.getEmail())) {
                throw new CustomException(ErrorResponse.ACCOUNT_CAN_NOT_CHANGE_EMAIL);
            }

            Account account = accountRepository.findByEmail(email)
                    .orElseThrow(() -> new NotFoundException("Account not found"));

            if(request.getNewFiles() != null && request.getNewFiles().size() > 1) {
                throw new CustomException(ErrorResponse.ACCOUNT_HAS_ONE_AVATAR);
            }

            modelMapper.map(request, account);

            String updatedAvatarUrl = "";
            if(request.getOldFileUrls() != null) {
                List<String> updatedImageUrls = cloudinaryService
                        .updateFiles(request.getOldFileUrls(), request.getNewFiles());
                updatedAvatarUrl = updatedImageUrls.get(0);
            }

            account.setImage(updatedAvatarUrl);

            accountRepository.save(account);

            return modelMapper.map(account, AccountResponse.class);
        } catch (NotFoundException e) {
            throw new NotFoundException(e.getMessage());
        } catch (CustomException e) {
            throw new CustomException(e.getErrorResponse());
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new CustomException(ErrorResponse.INTERNAL_SERVER);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public AccountResponse createAccount(AccountCreationRequest request) throws Exception {
        try {
            Role role = Role.builder().name(RoleEnum.CUSTOMER.name()).build();
            HashSet<Role> roles = new HashSet<>();
            roles.add(role);

            if (accountRepository.existsByEmail(request.getEmail())){
                throw new CustomException(ErrorResponse.DATA_EXISTED);
            }

            if(request.getFiles().size() > 1) {
                throw new CustomException(ErrorResponse.ACCOUNT_HAS_ONE_AVATAR);
            }

            Account account = modelMapper.map(request, Account.class);
            account.setPassword(passwordEncoder.encode(request.getPassword()));
            account.setRoles(roles);

            List<String> avatarUrl = cloudinaryService.uploadFiles(request.getFiles());
            account.setImage(avatarUrl.get(0));

            accountRepository.save(account);
            return modelMapper.map(account, AccountResponse.class);
        } catch (CustomException e) {
            throw new CustomException(e.getErrorResponse());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public AccountResponse updateAccount(UUID id, AccountUpdateRequest request) throws Exception {
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
