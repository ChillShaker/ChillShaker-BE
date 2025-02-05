package com.ducnt.chillshaker.service.implement;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ducnt.chillshaker.dto.request.account.AccountCreationRequest;
import com.ducnt.chillshaker.enums.AccountStatusEnum;
import com.ducnt.chillshaker.exception.CustomException;
import com.ducnt.chillshaker.exception.ErrorResponse;
import com.ducnt.chillshaker.exception.NotFoundException;
import com.ducnt.chillshaker.model.Account;
import com.ducnt.chillshaker.repository.AccountRepository;
import com.ducnt.chillshaker.repository.RoleRepository;
import com.ducnt.chillshaker.service.thirdparty.CloudinaryService;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

@ContextConfiguration(classes = {AccountService.class, PasswordEncoder.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class AccountServiceTest {
    @MockBean
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

    @MockBean
    private CloudinaryService cloudinaryService;

    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private RoleRepository roleRepository;

    /**
     * Test {@link AccountService#createAccount(AccountCreationRequest)}.
     * <p>
     * Method under test:
     * {@link AccountService#createAccount(AccountCreationRequest)}
     */
    @Test
    @DisplayName("Test createAccount(AccountCreationRequest)")
    void testCreateAccount() throws Exception {
        // Arrange
        when(accountRepository.existsByEmail(Mockito.<String>any()))
                .thenThrow(new CustomException(ErrorResponse.BAR_TABLE_IS_NOT_EMPTY));

        // Act and Assert
        assertThrows(CustomException.class, () -> accountService.createAccount(new AccountCreationRequest()));
        verify(accountRepository).existsByEmail(isNull());
    }

    /**
     * Test {@link AccountService#createAccount(AccountCreationRequest)}.
     * <p>
     * Method under test:
     * {@link AccountService#createAccount(AccountCreationRequest)}
     */
    @Test
    @DisplayName("Test createAccount(AccountCreationRequest)")
    void testCreateAccount2() throws Exception {
        // Arrange
        when(accountRepository.existsByEmail(Mockito.<String>any())).thenThrow(new NotFoundException("An error occurred"));

        // Act and Assert
        assertThrows(Exception.class, () -> accountService.createAccount(new AccountCreationRequest()));
        verify(accountRepository).existsByEmail(isNull());
    }

    /**
     * Test {@link AccountService#createAccount(AccountCreationRequest)}.
     * <p>
     * Method under test:
     * {@link AccountService#createAccount(AccountCreationRequest)}
     */
    @Test
    @DisplayName("Test createAccount(AccountCreationRequest)")
    void testCreateAccount3() throws Exception {
        // Arrange
        when(passwordEncoder.encode(Mockito.<CharSequence>any())).thenReturn("secret");
        when(accountRepository.existsByEmail(Mockito.<String>any())).thenReturn(false);

        Account account = new Account();
        account.setAddress("42 Main St");
        account.setBookings(new ArrayList<>());
        account.setCreatedDate(LocalDate.of(1970, 1, 1));
        account.setDob(LocalDate.of(1970, 1, 1));
        account.setEmail("jane.doe@example.org");
        account.setFullName("Dr Jane Doe");
        account.setId(UUID.randomUUID());
        account.setImage("Image");
        account.setModifiedDate(LocalDate.of(1970, 1, 1));
        account.setPassword("iloveyou");
        account.setPayment(new ArrayList<>());
        account.setPhone("6625550144");
        account.setRoles(new ArrayList<>());
        account.setStatus(AccountStatusEnum.NOT_VERIFIED);
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<Account>>any())).thenReturn(account);
        when(cloudinaryService.uploadFiles(Mockito.<List<MultipartFile>>any()))
                .thenThrow(new CustomException(ErrorResponse.BAR_TABLE_IS_NOT_EMPTY));

        ArrayList<MultipartFile> files = new ArrayList<>();
        files.add(new MockMultipartFile("Name", new ByteArrayInputStream("A;A;A;A;".getBytes("UTF-8"))));

        AccountCreationRequest request = new AccountCreationRequest();
        request.setFiles(files);

        // Act and Assert
        assertThrows(CustomException.class, () -> accountService.createAccount(request));
        verify(accountRepository).existsByEmail(isNull());
        verify(cloudinaryService).uploadFiles(isA(List.class));
        verify(modelMapper).map(isA(Object.class), isA(Class.class));
        verify(passwordEncoder).encode(isNull());
    }

    /**
     * Test {@link AccountService#createAccount(AccountCreationRequest)}.
     * <p>
     * Method under test:
     * {@link AccountService#createAccount(AccountCreationRequest)}
     */
    @Test
    @DisplayName("Test createAccount(AccountCreationRequest)")
    void testCreateAccount4() throws Exception {
        // Arrange
        when(passwordEncoder.encode(Mockito.<CharSequence>any())).thenReturn("secret");
        when(accountRepository.existsByEmail(Mockito.<String>any())).thenReturn(false);

        Account account = new Account();
        account.setAddress("42 Main St");
        account.setBookings(new ArrayList<>());
        account.setCreatedDate(LocalDate.of(1970, 1, 1));
        account.setDob(LocalDate.of(1970, 1, 1));
        account.setEmail("jane.doe@example.org");
        account.setFullName("Dr Jane Doe");
        account.setId(UUID.randomUUID());
        account.setImage("Image");
        account.setModifiedDate(LocalDate.of(1970, 1, 1));
        account.setPassword("iloveyou");
        account.setPayment(new ArrayList<>());
        account.setPhone("6625550144");
        account.setRoles(new ArrayList<>());
        account.setStatus(AccountStatusEnum.NOT_VERIFIED);
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<Account>>any())).thenReturn(account);
        when(cloudinaryService.uploadFiles(Mockito.<List<MultipartFile>>any()))
                .thenThrow(new NotFoundException("An error occurred"));

        ArrayList<MultipartFile> files = new ArrayList<>();
        files.add(new MockMultipartFile("Name", new ByteArrayInputStream("A;A;A;A;".getBytes("UTF-8"))));

        AccountCreationRequest request = new AccountCreationRequest();
        request.setFiles(files);

        // Act and Assert
        assertThrows(Exception.class, () -> accountService.createAccount(request));
        verify(accountRepository).existsByEmail(isNull());
        verify(cloudinaryService).uploadFiles(isA(List.class));
        verify(modelMapper).map(isA(Object.class), isA(Class.class));
        verify(passwordEncoder).encode(isNull());
    }

    /**
     * Test {@link AccountService#createAccount(AccountCreationRequest)}.
     * <ul>
     *   <li>Given {@link AccountRepository}
     * {@link AccountRepository#existsByEmail(String)} return {@code false}.</li>
     * </ul>
     * <p>
     * Method under test:
     * {@link AccountService#createAccount(AccountCreationRequest)}
     */
    @Test
    @DisplayName("Test createAccount(AccountCreationRequest); given AccountRepository existsByEmail(String) return 'false'")
    void testCreateAccount_givenAccountRepositoryExistsByEmailReturnFalse() throws Exception {
        // Arrange
        when(accountRepository.existsByEmail(Mockito.<String>any())).thenReturn(false);

        // Act and Assert
        assertThrows(Exception.class, () -> accountService.createAccount(new AccountCreationRequest()));
        verify(accountRepository).existsByEmail(isNull());
    }

    /**
     * Test {@link AccountService#createAccount(AccountCreationRequest)}.
     * <ul>
     *   <li>Given {@link AccountRepository}
     * {@link AccountRepository#existsByEmail(String)} return {@code true}.</li>
     * </ul>
     * <p>
     * Method under test:
     * {@link AccountService#createAccount(AccountCreationRequest)}
     */
    @Test
    @DisplayName("Test createAccount(AccountCreationRequest); given AccountRepository existsByEmail(String) return 'true'")
    void testCreateAccount_givenAccountRepositoryExistsByEmailReturnTrue() throws Exception {
        // Arrange
        when(accountRepository.existsByEmail(Mockito.<String>any())).thenReturn(true);

        // Act and Assert
        assertThrows(CustomException.class, () -> accountService.createAccount(new AccountCreationRequest()));
        verify(accountRepository).existsByEmail(isNull());
    }

    /**
     * Test {@link AccountService#createAccount(AccountCreationRequest)}.
     * <ul>
     *   <li>Given {@link CloudinaryService}
     * {@link CloudinaryService#uploadFiles(List)} return
     * {@link ArrayList#ArrayList()}.</li>
     * </ul>
     * <p>
     * Method under test:
     * {@link AccountService#createAccount(AccountCreationRequest)}
     */
    @Test
    @DisplayName("Test createAccount(AccountCreationRequest); given CloudinaryService uploadFiles(List) return ArrayList()")
    void testCreateAccount_givenCloudinaryServiceUploadFilesReturnArrayList() throws Exception {
        // Arrange
        when(passwordEncoder.encode(Mockito.<CharSequence>any())).thenReturn("secret");
        when(accountRepository.existsByEmail(Mockito.<String>any())).thenReturn(false);

        Account account = new Account();
        account.setAddress("42 Main St");
        account.setBookings(new ArrayList<>());
        account.setCreatedDate(LocalDate.of(1970, 1, 1));
        account.setDob(LocalDate.of(1970, 1, 1));
        account.setEmail("jane.doe@example.org");
        account.setFullName("Dr Jane Doe");
        account.setId(UUID.randomUUID());
        account.setImage("Image");
        account.setModifiedDate(LocalDate.of(1970, 1, 1));
        account.setPassword("iloveyou");
        account.setPayment(new ArrayList<>());
        account.setPhone("6625550144");
        account.setRoles(new ArrayList<>());
        account.setStatus(AccountStatusEnum.NOT_VERIFIED);
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<Account>>any())).thenReturn(account);
        when(cloudinaryService.uploadFiles(Mockito.<List<MultipartFile>>any())).thenReturn(new ArrayList<>());

        ArrayList<MultipartFile> files = new ArrayList<>();
        files.add(new MockMultipartFile("Name", new ByteArrayInputStream("A;A;A;A;".getBytes("UTF-8"))));

        AccountCreationRequest request = new AccountCreationRequest();
        request.setFiles(files);

        // Act and Assert
        assertThrows(Exception.class, () -> accountService.createAccount(request));
        verify(accountRepository).existsByEmail(isNull());
        verify(cloudinaryService).uploadFiles(isA(List.class));
        verify(modelMapper).map(isA(Object.class), isA(Class.class));
        verify(passwordEncoder).encode(isNull());
    }

    /**
     * Test {@link AccountService#createAccount(AccountCreationRequest)}.
     * <ul>
     *   <li>Given {@link PasswordEncoder}.</li>
     *   <li>When {@code null}.</li>
     *   <li>Then throw {@link Exception}.</li>
     * </ul>
     * <p>
     * Method under test:
     * {@link AccountService#createAccount(AccountCreationRequest)}
     */
    @Test
    @DisplayName("Test createAccount(AccountCreationRequest); given PasswordEncoder; when 'null'; then throw Exception")
    void testCreateAccount_givenPasswordEncoder_whenNull_thenThrowException() throws Exception {
        // Arrange, Act and Assert
        assertThrows(Exception.class, () -> accountService.createAccount(null));
    }

    /**
     * Test {@link AccountService#createAccount(AccountCreationRequest)}.
     * <ul>
     *   <li>Then calls {@link AccountCreationRequest#getEmail()}.</li>
     * </ul>
     * <p>
     * Method under test:
     * {@link AccountService#createAccount(AccountCreationRequest)}
     */
    @Test
    @DisplayName("Test createAccount(AccountCreationRequest); then calls getEmail()")
    void testCreateAccount_thenCallsGetEmail() throws Exception {
        // Arrange
        AccountCreationRequest request = mock(AccountCreationRequest.class);
        when(request.getEmail()).thenThrow(new CustomException(ErrorResponse.BAR_TABLE_IS_NOT_EMPTY));

        // Act and Assert
        assertThrows(CustomException.class, () -> accountService.createAccount(request));
        verify(request).getEmail();
    }
}
