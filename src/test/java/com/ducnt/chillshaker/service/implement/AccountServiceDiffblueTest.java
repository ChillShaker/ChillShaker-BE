package com.ducnt.chillshaker.service.implement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.Md4PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {AccountService.class, PasswordEncoder.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class AccountServiceDiffblueTest {
    @MockBean
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private RoleRepository roleRepository;

    /**
     * Method under test: {@link AccountService#getAllAccounts()}
     */
    @Test
    void testGetAllAccounts() {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

        // Arrange
        AccountRepository accountRepository = mock(AccountRepository.class);
        when(accountRepository.findAll()).thenReturn(new ArrayList<>());
        RoleRepository roleRepository = mock(RoleRepository.class);
        ModelMapper modelMapper = new ModelMapper();

        // Act
        List<AccountResponse> actualAllAccounts = (new AccountService(accountRepository, roleRepository, modelMapper,
                new BCryptPasswordEncoder())).getAllAccounts();

        // Assert
        verify(accountRepository).findAll();
        assertTrue(actualAllAccounts.isEmpty());
    }

    /**
     * Method under test: {@link AccountService#getAllAccounts()}
     */
    @Test
    void testGetAllAccounts2() {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

        // Arrange
        Account account = new Account();
        account.setAddress("42 Main St");
        account.setCreatedDate(LocalDate.of(1970, 1, 1));
        account.setDob(LocalDate.of(1970, 1, 1));
        account.setEmail("jane.doe@example.org");
        account.setFullName("Dr Jane Doe");
        account.setId(UUID.randomUUID());
        account.setModifiedDate(LocalDate.of(1970, 1, 1));
        account.setPassword("iloveyou");
        account.setPhone("6625550144");
        account.setRoles(new ArrayList<>());

        ArrayList<Account> accountList = new ArrayList<>();
        accountList.add(account);
        AccountRepository accountRepository = mock(AccountRepository.class);
        when(accountRepository.findAll()).thenReturn(accountList);
        RoleRepository roleRepository = mock(RoleRepository.class);
        ModelMapper modelMapper = new ModelMapper();

        // Act
        List<AccountResponse> actualAllAccounts = (new AccountService(accountRepository, roleRepository, modelMapper,
                new BCryptPasswordEncoder())).getAllAccounts();

        // Assert
        verify(accountRepository).findAll();
        assertEquals(1, actualAllAccounts.size());
    }

    /**
     * Method under test: {@link AccountService#getAllAccounts()}
     */
    @Test
    void testGetAllAccounts3() {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

        // Arrange
        Account account = new Account();
        account.setAddress("42 Main St");
        account.setCreatedDate(LocalDate.of(1970, 1, 1));
        account.setDob(LocalDate.of(1970, 1, 1));
        account.setEmail("jane.doe@example.org");
        account.setFullName("Dr Jane Doe");
        account.setId(UUID.randomUUID());
        account.setModifiedDate(LocalDate.of(1970, 1, 1));
        account.setPassword("iloveyou");
        account.setPhone("6625550144");
        account.setRoles(new ArrayList<>());

        Account account2 = new Account();
        account2.setAddress("17 High St");
        account2.setCreatedDate(LocalDate.of(1970, 1, 1));
        account2.setDob(LocalDate.of(1970, 1, 1));
        account2.setEmail("john.smith@example.org");
        account2.setFullName("Mr John Smith");
        account2.setId(UUID.randomUUID());
        account2.setModifiedDate(LocalDate.of(1970, 1, 1));
        account2.setPassword("Password");
        account2.setPhone("8605550118");
        account2.setRoles(new ArrayList<>());

        ArrayList<Account> accountList = new ArrayList<>();
        accountList.add(account2);
        accountList.add(account);
        AccountRepository accountRepository = mock(AccountRepository.class);
        when(accountRepository.findAll()).thenReturn(accountList);
        RoleRepository roleRepository = mock(RoleRepository.class);
        ModelMapper modelMapper = new ModelMapper();

        // Act
        List<AccountResponse> actualAllAccounts = (new AccountService(accountRepository, roleRepository, modelMapper,
                new BCryptPasswordEncoder())).getAllAccounts();

        // Assert
        verify(accountRepository).findAll();
        assertEquals(2, actualAllAccounts.size());
    }

    /**
     * Method under test: {@link AccountService#getAllAccounts()}
     */
    @Test
    void testGetAllAccounts4() {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

        // Arrange
        AccountRepository accountRepository = mock(AccountRepository.class);
        when(accountRepository.findAll()).thenThrow(new CustomException(ErrorResponse.FULLNAME_INVALID));
        RoleRepository roleRepository = mock(RoleRepository.class);
        ModelMapper modelMapper = new ModelMapper();

        // Act and Assert
        assertThrows(CustomException.class,
                () -> (new AccountService(accountRepository, roleRepository, modelMapper, new BCryptPasswordEncoder()))
                        .getAllAccounts());
        verify(accountRepository).findAll();
    }

    /**
     * Method under test: {@link AccountService#getAllAccounts()}
     */
    @Test
    void testGetAllAccounts5() {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

        // Arrange
        Account account = new Account();
        account.setAddress("42 Main St");
        account.setCreatedDate(LocalDate.of(1970, 1, 1));
        account.setDob(null);
        account.setEmail("jane.doe@example.org");
        account.setFullName("Dr Jane Doe");
        account.setId(UUID.randomUUID());
        account.setModifiedDate(LocalDate.of(1970, 1, 1));
        account.setPassword("iloveyou");
        account.setPhone("6625550144");
        account.setRoles(new ArrayList<>());

        ArrayList<Account> accountList = new ArrayList<>();
        accountList.add(account);
        AccountRepository accountRepository = mock(AccountRepository.class);
        when(accountRepository.findAll()).thenReturn(accountList);
        RoleRepository roleRepository = mock(RoleRepository.class);
        ModelMapper modelMapper = new ModelMapper();

        // Act
        List<AccountResponse> actualAllAccounts = (new AccountService(accountRepository, roleRepository, modelMapper,
                new BCryptPasswordEncoder())).getAllAccounts();

        // Assert
        verify(accountRepository).findAll();
        assertEquals(1, actualAllAccounts.size());
    }

    /**
     * Method under test: {@link AccountService#getAllAccounts()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetAllAccounts6() {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Reason: Failed to create Spring context.
        //   Attempt to initialize test context failed with
        //   java.lang.IllegalStateException: ApplicationContext failure threshold (1) exceeded: skipping repeated attempt to load context for [MergedContextConfiguration@782f46b6 testClass = com.ducnt.chillshaker.service.implement.DiffblueFakeClass231, locations = [], classes = [com.ducnt.chillshaker.service.implement.AccountService, org.springframework.security.crypto.password.PasswordEncoder], contextInitializerClasses = [], activeProfiles = [], propertySourceDescriptors = [], propertySourceProperties = [], contextCustomizers = [org.springframework.boot.test.context.filter.ExcludeFilterContextCustomizer@1765cb2e, org.springframework.boot.test.json.DuplicateJsonObjectContextCustomizerFactory$DuplicateJsonObjectContextCustomizer@67d9dde7, org.springframework.boot.test.mock.mockito.MockitoContextCustomizer@46708f0f, org.springframework.boot.test.web.reactor.netty.DisableReactorResourceFactoryGlobalResourcesContextCustomizerFactory$DisableReactorResourceFactoryGlobalResourcesContextCustomizerCustomizer@78a88d23, org.springframework.boot.test.autoconfigure.OnFailureConditionReportContextCustomizerFactory$OnFailureConditionReportContextCustomizer@33335f2b, org.springframework.boot.test.autoconfigure.actuate.observability.ObservabilityContextCustomizerFactory$DisableObservabilityContextCustomizer@1f, org.springframework.boot.test.autoconfigure.properties.PropertyMappingContextCustomizer@0, org.springframework.boot.test.autoconfigure.web.servlet.WebDriverContextCustomizer@620967ba], contextLoader = org.springframework.test.context.support.DelegatingSmartContextLoader, parent = null]
        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:145)
        //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:130)
        //       at java.base/java.util.Optional.map(Optional.java:260)
        //   See https://diff.blue/R026 to resolve this issue.

        // Arrange and Act
        accountService.getAllAccounts();
    }

    /**
     * Method under test: {@link AccountService#getAccountById(UUID)}
     */
    @Test
    void testGetAccountById() {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

        // Arrange
        Account account = new Account();
        account.setAddress("42 Main St");
        account.setCreatedDate(LocalDate.of(1970, 1, 1));
        account.setDob(LocalDate.of(1970, 1, 1));
        account.setEmail("jane.doe@example.org");
        account.setFullName("Dr Jane Doe");
        account.setId(UUID.randomUUID());
        account.setModifiedDate(LocalDate.of(1970, 1, 1));
        account.setPassword("iloveyou");
        account.setPhone("6625550144");
        account.setRoles(new ArrayList<>());
        Optional<Account> ofResult = Optional.of(account);
        AccountRepository accountRepository = mock(AccountRepository.class);
        when(accountRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult);
        RoleRepository roleRepository = mock(RoleRepository.class);
        ModelMapper modelMapper = new ModelMapper();
        AccountService accountService = new AccountService(accountRepository, roleRepository, modelMapper,
                new BCryptPasswordEncoder());

        // Act
        AccountResponse actualAccountById = accountService.getAccountById(UUID.randomUUID());

        // Assert
        verify(accountRepository).findById(isA(UUID.class));
        assertEquals("1970-01-01", actualAccountById.getDob().toString());
        assertEquals("42 Main St", actualAccountById.getAddress());
        assertEquals("6625550144", actualAccountById.getPhone());
        assertEquals("Dr Jane Doe", actualAccountById.getFullName());
        assertEquals("jane.doe@example.org", actualAccountById.getEmail());
        assertEquals(2, actualAccountById.getId().variant());
    }

    /**
     * Method under test: {@link AccountService#getAccountById(UUID)}
     */
    @Test
    void testGetAccountById2() {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

        // Arrange
        Account.AccountBuilder builderResult = Account.builder();
        Account.AccountBuilder phoneResult = builderResult.dob(LocalDate.of(1970, 1, 1))
                .email("jane.doe@example.org")
                .fullName("Dr Jane Doe")
                .password("iloveyou")
                .phone("6625550144");
        Account buildResult = phoneResult.roles(new ArrayList<>()).build();
        buildResult.setAddress("42 Main St");
        buildResult.setCreatedDate(LocalDate.of(1970, 1, 1));
        buildResult.setDob(LocalDate.of(1970, 1, 1));
        buildResult.setEmail("jane.doe@example.org");
        buildResult.setFullName("Dr Jane Doe");
        buildResult.setId(UUID.randomUUID());
        buildResult.setModifiedDate(LocalDate.of(1970, 1, 1));
        buildResult.setPassword("iloveyou");
        buildResult.setPhone("6625550144");
        buildResult.setRoles(new ArrayList<>());
        Optional<Account> ofResult = Optional.of(buildResult);
        AccountRepository accountRepository = mock(AccountRepository.class);
        when(accountRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult);
        RoleRepository roleRepository = mock(RoleRepository.class);
        ModelMapper modelMapper = new ModelMapper();
        AccountService accountService = new AccountService(accountRepository, roleRepository, modelMapper,
                new BCryptPasswordEncoder());

        // Act
        AccountResponse actualAccountById = accountService.getAccountById(UUID.randomUUID());

        // Assert
        verify(accountRepository).findById(isA(UUID.class));
        assertEquals("1970-01-01", actualAccountById.getDob().toString());
        assertEquals("42 Main St", actualAccountById.getAddress());
        assertEquals("6625550144", actualAccountById.getPhone());
        assertEquals("Dr Jane Doe", actualAccountById.getFullName());
        assertEquals("jane.doe@example.org", actualAccountById.getEmail());
        assertEquals(2, actualAccountById.getId().variant());
    }

    /**
     * Method under test: {@link AccountService#getAccountById(UUID)}
     */
    @Test
    void testGetAccountById3() {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

        // Arrange
        Account account = new Account();
        account.setAddress("42 Main St");
        account.setCreatedDate(LocalDate.of(1970, 1, 1));
        account.setDob(null);
        account.setEmail("jane.doe@example.org");
        account.setFullName("Dr Jane Doe");
        account.setId(UUID.randomUUID());
        account.setModifiedDate(LocalDate.of(1970, 1, 1));
        account.setPassword("iloveyou");
        account.setPhone("6625550144");
        account.setRoles(new ArrayList<>());
        Optional<Account> ofResult = Optional.of(account);
        AccountRepository accountRepository = mock(AccountRepository.class);
        when(accountRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult);
        RoleRepository roleRepository = mock(RoleRepository.class);
        ModelMapper modelMapper = new ModelMapper();
        AccountService accountService = new AccountService(accountRepository, roleRepository, modelMapper,
                new BCryptPasswordEncoder());

        // Act
        AccountResponse actualAccountById = accountService.getAccountById(UUID.randomUUID());

        // Assert
        verify(accountRepository).findById(isA(UUID.class));
        assertEquals("42 Main St", actualAccountById.getAddress());
        assertEquals("6625550144", actualAccountById.getPhone());
        assertEquals("Dr Jane Doe", actualAccountById.getFullName());
        assertEquals("jane.doe@example.org", actualAccountById.getEmail());
        assertNull(actualAccountById.getDob());
        assertEquals(2, actualAccountById.getId().variant());
    }

    /**
     * Method under test: {@link AccountService#getAccountById(UUID)}
     */
    @Test
    void testGetAccountById4() {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

        // Arrange
        AccountRepository accountRepository = mock(AccountRepository.class);
        Optional<Account> emptyResult = Optional.empty();
        when(accountRepository.findById(Mockito.<UUID>any())).thenReturn(emptyResult);
        RoleRepository roleRepository = mock(RoleRepository.class);
        ModelMapper modelMapper = new ModelMapper();
        AccountService accountService = new AccountService(accountRepository, roleRepository, modelMapper,
                new BCryptPasswordEncoder());

        // Act and Assert
        assertThrows(NotFoundException.class, () -> accountService.getAccountById(UUID.randomUUID()));
        verify(accountRepository).findById(isA(UUID.class));
    }

    /**
     * Method under test: {@link AccountService#getAccountById(UUID)}
     */
    @Test
    void testGetAccountById5() {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

        // Arrange
        AccountRepository accountRepository = mock(AccountRepository.class);
        when(accountRepository.findById(Mockito.<UUID>any()))
                .thenThrow(new CustomException(ErrorResponse.FULLNAME_INVALID));
        RoleRepository roleRepository = mock(RoleRepository.class);
        ModelMapper modelMapper = new ModelMapper();
        AccountService accountService = new AccountService(accountRepository, roleRepository, modelMapper,
                new BCryptPasswordEncoder());

        // Act and Assert
        assertThrows(CustomException.class, () -> accountService.getAccountById(UUID.randomUUID()));
        verify(accountRepository).findById(isA(UUID.class));
    }

    /**
     * Method under test: {@link AccountService#getAccountById(UUID)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetAccountById6() {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Reason: Failed to create Spring context.
        //   Attempt to initialize test context failed with
        //   java.lang.IllegalStateException: ApplicationContext failure threshold (1) exceeded: skipping repeated attempt to load context for [MergedContextConfiguration@33b2f9f4 testClass = com.ducnt.chillshaker.service.implement.DiffblueFakeClass226, locations = [], classes = [com.ducnt.chillshaker.service.implement.AccountService, org.springframework.security.crypto.password.PasswordEncoder], contextInitializerClasses = [], activeProfiles = [], propertySourceDescriptors = [], propertySourceProperties = [], contextCustomizers = [org.springframework.boot.test.context.filter.ExcludeFilterContextCustomizer@1765cb2e, org.springframework.boot.test.json.DuplicateJsonObjectContextCustomizerFactory$DuplicateJsonObjectContextCustomizer@67d9dde7, org.springframework.boot.test.mock.mockito.MockitoContextCustomizer@46708f0f, org.springframework.boot.test.web.reactor.netty.DisableReactorResourceFactoryGlobalResourcesContextCustomizerFactory$DisableReactorResourceFactoryGlobalResourcesContextCustomizerCustomizer@78a88d23, org.springframework.boot.test.autoconfigure.OnFailureConditionReportContextCustomizerFactory$OnFailureConditionReportContextCustomizer@33335f2b, org.springframework.boot.test.autoconfigure.actuate.observability.ObservabilityContextCustomizerFactory$DisableObservabilityContextCustomizer@1f, org.springframework.boot.test.autoconfigure.properties.PropertyMappingContextCustomizer@0, org.springframework.boot.test.autoconfigure.web.servlet.WebDriverContextCustomizer@620967ba], contextLoader = org.springframework.test.context.support.DelegatingSmartContextLoader, parent = null]
        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:145)
        //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:130)
        //       at java.base/java.util.Optional.map(Optional.java:260)
        //   See https://diff.blue/R026 to resolve this issue.

        // Arrange and Act
        accountService.getAccountById(UUID.randomUUID());
    }

    /**
     * Method under test: {@link AccountService#getMyInfo()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetMyInfo() {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Reason: Failed to create Spring context.
        //   Attempt to initialize test context failed with
        //   java.lang.IllegalStateException: ApplicationContext failure threshold (1) exceeded: skipping repeated attempt to load context for [MergedContextConfiguration@5af091e8 testClass = com.ducnt.chillshaker.service.implement.DiffblueFakeClass233, locations = [], classes = [com.ducnt.chillshaker.service.implement.AccountService, org.springframework.security.crypto.password.PasswordEncoder], contextInitializerClasses = [], activeProfiles = [], propertySourceDescriptors = [], propertySourceProperties = [], contextCustomizers = [org.springframework.boot.test.context.filter.ExcludeFilterContextCustomizer@1765cb2e, org.springframework.boot.test.json.DuplicateJsonObjectContextCustomizerFactory$DuplicateJsonObjectContextCustomizer@67d9dde7, org.springframework.boot.test.mock.mockito.MockitoContextCustomizer@46708f0f, org.springframework.boot.test.web.reactor.netty.DisableReactorResourceFactoryGlobalResourcesContextCustomizerFactory$DisableReactorResourceFactoryGlobalResourcesContextCustomizerCustomizer@78a88d23, org.springframework.boot.test.autoconfigure.OnFailureConditionReportContextCustomizerFactory$OnFailureConditionReportContextCustomizer@33335f2b, org.springframework.boot.test.autoconfigure.actuate.observability.ObservabilityContextCustomizerFactory$DisableObservabilityContextCustomizer@1f, org.springframework.boot.test.autoconfigure.properties.PropertyMappingContextCustomizer@0, org.springframework.boot.test.autoconfigure.web.servlet.WebDriverContextCustomizer@620967ba], contextLoader = org.springframework.test.context.support.DelegatingSmartContextLoader, parent = null]
        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:145)
        //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:130)
        //       at java.base/java.util.Optional.map(Optional.java:260)
        //   See https://diff.blue/R026 to resolve this issue.

        // Arrange and Act
        accountService.getMyInfo();
    }

    /**
     * Method under test:
     * {@link AccountService#createAccount(AccountCreationRequest)}
     */
    @Test
    void testCreateAccount() throws Exception {
        // Arrange
        when(accountRepository.existsByEmail(Mockito.<String>any())).thenReturn(true);

        // Act and Assert
        assertThrows(Exception.class, () -> accountService.createAccount(new AccountCreationRequest()));
        verify(accountRepository).existsByEmail(isNull());
    }

    /**
     * Method under test:
     * {@link AccountService#createAccount(AccountCreationRequest)}
     */
    @Test
    void testCreateAccount2() throws Exception {
        // Arrange
        when(accountRepository.existsByEmail(Mockito.<String>any()))
                .thenThrow(new CustomException(ErrorResponse.FULLNAME_INVALID));

        // Act and Assert
        assertThrows(Exception.class, () -> accountService.createAccount(new AccountCreationRequest()));
        verify(accountRepository).existsByEmail(isNull());
    }

    /**
     * Method under test:
     * {@link AccountService#createAccount(AccountCreationRequest)}
     */
    @Test
    void testCreateAccount3() throws Exception {
        // Arrange
        when(passwordEncoder.encode(Mockito.<CharSequence>any())).thenReturn("secret");

        Account account = new Account();
        account.setAddress("42 Main St");
        account.setCreatedDate(LocalDate.of(1970, 1, 1));
        account.setDob(LocalDate.of(1970, 1, 1));
        account.setEmail("jane.doe@example.org");
        account.setFullName("Dr Jane Doe");
        account.setId(UUID.randomUUID());
        account.setModifiedDate(LocalDate.of(1970, 1, 1));
        account.setPassword("iloveyou");
        account.setPhone("6625550144");
        account.setRoles(new ArrayList<>());
        when(accountRepository.existsByEmail(Mockito.<String>any())).thenReturn(false);
        when(accountRepository.save(Mockito.<Account>any())).thenReturn(account);

        Account account2 = new Account();
        account2.setAddress("42 Main St");
        account2.setCreatedDate(LocalDate.of(1970, 1, 1));
        account2.setDob(LocalDate.of(1970, 1, 1));
        account2.setEmail("jane.doe@example.org");
        account2.setFullName("Dr Jane Doe");
        account2.setId(UUID.randomUUID());
        account2.setModifiedDate(LocalDate.of(1970, 1, 1));
        account2.setPassword("iloveyou");
        account2.setPhone("6625550144");
        account2.setRoles(new ArrayList<>());
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<Account>>any())).thenReturn(account2);

        // Act and Assert
        assertThrows(Exception.class, () -> accountService.createAccount(new AccountCreationRequest()));
        verify(accountRepository).existsByEmail(isNull());
        verify(modelMapper, atLeast(1)).map(Mockito.<Object>any(), Mockito.<Class<Object>>any());
        verify(accountRepository).save(isA(Account.class));
        verify(passwordEncoder).encode(isNull());
    }

    /**
     * Method under test:
     * {@link AccountService#createAccount(AccountCreationRequest)}
     */
    @Test
    void testCreateAccount4() throws Exception {
        // Arrange
        Account account = new Account();
        account.setAddress("42 Main St");
        account.setCreatedDate(LocalDate.of(1970, 1, 1));
        account.setDob(LocalDate.of(1970, 1, 1));
        account.setEmail("jane.doe@example.org");
        account.setFullName("Dr Jane Doe");
        account.setId(UUID.randomUUID());
        account.setModifiedDate(LocalDate.of(1970, 1, 1));
        account.setPassword("iloveyou");
        account.setPhone("6625550144");
        account.setRoles(new ArrayList<>());
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<Object>>any())).thenReturn("Map");
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<Account>>any())).thenReturn(account);

        // Act and Assert
        assertThrows(Exception.class, () -> accountService.createAccount(null));
        verify(modelMapper).map(Mockito.<Object>any(), Mockito.<Class<Object>>any());
    }

    /**
     * Method under test:
     * {@link AccountService#updateAccount(UUID, AccountUpdationRequest)}
     */
    @Test
    void testUpdateAccount() throws Exception {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

        // Arrange
        Account account = new Account();
        account.setAddress("42 Main St");
        account.setCreatedDate(LocalDate.of(1970, 1, 1));
        account.setDob(LocalDate.of(1970, 1, 1));
        account.setEmail("jane.doe@example.org");
        account.setFullName("Dr Jane Doe");
        account.setId(UUID.randomUUID());
        account.setModifiedDate(LocalDate.of(1970, 1, 1));
        account.setPassword("iloveyou");
        account.setPhone("6625550144");
        account.setRoles(new ArrayList<>());
        Optional<Account> ofResult = Optional.of(account);
        AccountRepository accountRepository = mock(AccountRepository.class);
        when(accountRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult);
        RoleRepository roleRepository = mock(RoleRepository.class);
        ModelMapper modelMapper = new ModelMapper();
        AccountService accountService = new AccountService(accountRepository, roleRepository, modelMapper,
                new BCryptPasswordEncoder());
        UUID id = UUID.randomUUID();

        // Act and Assert
        assertThrows(Exception.class, () -> accountService.updateAccount(id, new AccountUpdationRequest()));
        verify(accountRepository).findById(isA(UUID.class));
    }

    /**
     * Method under test:
     * {@link AccountService#updateAccount(UUID, AccountUpdationRequest)}
     */
    @Test
    void testUpdateAccount2() throws Exception {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

        // Arrange
        AccountRepository accountRepository = mock(AccountRepository.class);
        Optional<Account> emptyResult = Optional.empty();
        when(accountRepository.findById(Mockito.<UUID>any())).thenReturn(emptyResult);
        RoleRepository roleRepository = mock(RoleRepository.class);
        ModelMapper modelMapper = new ModelMapper();
        AccountService accountService = new AccountService(accountRepository, roleRepository, modelMapper,
                new BCryptPasswordEncoder());
        UUID id = UUID.randomUUID();

        // Act and Assert
        assertThrows(NotFoundException.class, () -> accountService.updateAccount(id, new AccountUpdationRequest()));
        verify(accountRepository).findById(isA(UUID.class));
    }

    /**
     * Method under test:
     * {@link AccountService#updateAccount(UUID, AccountUpdationRequest)}
     */
    @Test
    void testUpdateAccount3() throws Exception {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

        // Arrange
        Account account = new Account();
        account.setAddress("42 Main St");
        account.setCreatedDate(LocalDate.of(1970, 1, 1));
        account.setDob(LocalDate.of(1970, 1, 1));
        account.setEmail("jane.doe@example.org");
        account.setFullName("Dr Jane Doe");
        account.setId(UUID.randomUUID());
        account.setModifiedDate(LocalDate.of(1970, 1, 1));
        account.setPassword("iloveyou");
        account.setPhone("6625550144");
        account.setRoles(new ArrayList<>());
        Optional<Account> ofResult = Optional.of(account);
        AccountRepository accountRepository = mock(AccountRepository.class);
        when(accountRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult);
        RoleRepository roleRepository = mock(RoleRepository.class);
        AccountService accountService = new AccountService(accountRepository, roleRepository, null,
                new BCryptPasswordEncoder());
        UUID id = UUID.randomUUID();

        // Act and Assert
        assertThrows(Exception.class, () -> accountService.updateAccount(id, new AccountUpdationRequest()));
        verify(accountRepository).findById(isA(UUID.class));
    }

    /**
     * Method under test:
     * {@link AccountService#updateAccount(UUID, AccountUpdationRequest)}
     */
    @Test
    void testUpdateAccount4() throws Exception {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

        // Arrange
        Account account = new Account();
        account.setAddress("42 Main St");
        account.setCreatedDate(LocalDate.of(1970, 1, 1));
        account.setDob(LocalDate.of(1970, 1, 1));
        account.setEmail("jane.doe@example.org");
        account.setFullName("Dr Jane Doe");
        account.setId(UUID.randomUUID());
        account.setModifiedDate(LocalDate.of(1970, 1, 1));
        account.setPassword("iloveyou");
        account.setPhone("6625550144");
        account.setRoles(new ArrayList<>());
        Optional<Account> ofResult = Optional.of(account);

        Account account2 = new Account();
        account2.setAddress("42 Main St");
        account2.setCreatedDate(LocalDate.of(1970, 1, 1));
        account2.setDob(LocalDate.of(1970, 1, 1));
        account2.setEmail("jane.doe@example.org");
        account2.setFullName("Dr Jane Doe");
        account2.setId(UUID.randomUUID());
        account2.setModifiedDate(LocalDate.of(1970, 1, 1));
        account2.setPassword("iloveyou");
        account2.setPhone("6625550144");
        account2.setRoles(new ArrayList<>());
        AccountRepository accountRepository = mock(AccountRepository.class);
        when(accountRepository.save(Mockito.<Account>any())).thenReturn(account2);
        when(accountRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult);
        RoleRepository roleRepository = mock(RoleRepository.class);
        ModelMapper modelMapper = new ModelMapper();
        AccountService accountService = new AccountService(accountRepository, roleRepository, modelMapper,
                new Md4PasswordEncoder());
        UUID id = UUID.randomUUID();

        // Act
        AccountResponse actualUpdateAccountResult = accountService.updateAccount(id, new AccountUpdationRequest());

        // Assert
        verify(accountRepository).findById(isA(UUID.class));
        verify(accountRepository).save(isA(Account.class));
        assertEquals("1970-01-01", actualUpdateAccountResult.getDob().toString());
        assertNull(actualUpdateAccountResult.getAddress());
        assertNull(actualUpdateAccountResult.getEmail());
        assertNull(actualUpdateAccountResult.getFullName());
        assertNull(actualUpdateAccountResult.getPhone());
        assertEquals(2, actualUpdateAccountResult.getId().variant());
    }

    /**
     * Method under test:
     * {@link AccountService#updateAccount(UUID, AccountUpdationRequest)}
     */
    @Test
    void testUpdateAccount5() throws Exception {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

        // Arrange
        Account account = new Account();
        account.setAddress("42 Main St");
        account.setCreatedDate(LocalDate.of(1970, 1, 1));
        account.setDob(LocalDate.of(1970, 1, 1));
        account.setEmail("jane.doe@example.org");
        account.setFullName("Dr Jane Doe");
        account.setId(UUID.randomUUID());
        account.setModifiedDate(LocalDate.of(1970, 1, 1));
        account.setPassword("iloveyou");
        account.setPhone("6625550144");
        account.setRoles(new ArrayList<>());
        Optional<Account> ofResult = Optional.of(account);
        AccountRepository accountRepository = mock(AccountRepository.class);
        when(accountRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult);
        RoleRepository roleRepository = mock(RoleRepository.class);
        ModelMapper modelMapper = new ModelMapper();
        AccountService accountService = new AccountService(accountRepository, roleRepository, modelMapper,
                new Md4PasswordEncoder());

        // Act and Assert
        assertThrows(Exception.class, () -> accountService.updateAccount(UUID.randomUUID(), null));
        verify(accountRepository).findById(isA(UUID.class));
    }

    /**
     * Method under test:
     * {@link AccountService#updateAccount(UUID, AccountUpdationRequest)}
     */
    @Test
    void testUpdateAccount6() throws Exception {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

        // Arrange
        Account account = new Account();
        account.setAddress("42 Main St");
        account.setCreatedDate(LocalDate.of(1970, 1, 1));
        account.setDob(LocalDate.of(1970, 1, 1));
        account.setEmail("jane.doe@example.org");
        account.setFullName("Dr Jane Doe");
        account.setId(UUID.randomUUID());
        account.setModifiedDate(LocalDate.of(1970, 1, 1));
        account.setPassword("iloveyou");
        account.setPhone("6625550144");
        account.setRoles(new ArrayList<>());
        Optional<Account> ofResult = Optional.of(account);
        AccountRepository accountRepository = mock(AccountRepository.class);
        when(accountRepository.save(Mockito.<Account>any())).thenThrow(new CustomException(ErrorResponse.FULLNAME_INVALID));
        when(accountRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult);
        RoleRepository roleRepository = mock(RoleRepository.class);
        ModelMapper modelMapper = new ModelMapper();
        AccountService accountService = new AccountService(accountRepository, roleRepository, modelMapper,
                new Md4PasswordEncoder());
        UUID id = UUID.randomUUID();

        // Act and Assert
        assertThrows(Exception.class, () -> accountService.updateAccount(id, new AccountUpdationRequest()));
        verify(accountRepository).findById(isA(UUID.class));
        verify(accountRepository).save(isA(Account.class));
    }

    /**
     * Method under test:
     * {@link AccountService#updateAccount(UUID, AccountUpdationRequest)}
     */
    @Test
    void testUpdateAccount7() throws Exception {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

        // Arrange
        Account account = new Account();
        account.setAddress("42 Main St");
        account.setCreatedDate(LocalDate.of(1970, 1, 1));
        account.setDob(LocalDate.of(1970, 1, 1));
        account.setEmail("jane.doe@example.org");
        account.setFullName("Dr Jane Doe");
        account.setId(UUID.randomUUID());
        account.setModifiedDate(LocalDate.of(1970, 1, 1));
        account.setPassword("iloveyou");
        account.setPhone("6625550144");
        account.setRoles(new ArrayList<>());
        Optional<Account> ofResult = Optional.of(account);
        AccountRepository accountRepository = mock(AccountRepository.class);
        when(accountRepository.save(Mockito.<Account>any())).thenThrow(new NotFoundException("An error occurred"));
        when(accountRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult);
        RoleRepository roleRepository = mock(RoleRepository.class);
        ModelMapper modelMapper = new ModelMapper();
        AccountService accountService = new AccountService(accountRepository, roleRepository, modelMapper,
                new Md4PasswordEncoder());
        UUID id = UUID.randomUUID();

        // Act and Assert
        assertThrows(NotFoundException.class, () -> accountService.updateAccount(id, new AccountUpdationRequest()));
        verify(accountRepository).findById(isA(UUID.class));
        verify(accountRepository).save(isA(Account.class));
    }

    /**
     * Method under test:
     * {@link AccountService#updateAccount(UUID, AccountUpdationRequest)}
     */
    @Test
    void testUpdateAccount8() throws Exception {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

        // Arrange
        Account account = new Account();
        account.setAddress("42 Main St");
        account.setCreatedDate(LocalDate.of(1970, 1, 1));
        account.setDob(LocalDate.of(1970, 1, 1));
        account.setEmail("jane.doe@example.org");
        account.setFullName("Dr Jane Doe");
        account.setId(UUID.randomUUID());
        account.setModifiedDate(LocalDate.of(1970, 1, 1));
        account.setPassword("iloveyou");
        account.setPhone("6625550144");
        account.setRoles(new ArrayList<>());
        Optional<Account> ofResult = Optional.of(account);

        Account account2 = new Account();
        account2.setAddress("42 Main St");
        account2.setCreatedDate(LocalDate.of(1970, 1, 1));
        account2.setDob(LocalDate.of(1970, 1, 1));
        account2.setEmail("jane.doe@example.org");
        account2.setFullName("Dr Jane Doe");
        account2.setId(UUID.randomUUID());
        account2.setModifiedDate(LocalDate.of(1970, 1, 1));
        account2.setPassword("iloveyou");
        account2.setPhone("6625550144");
        account2.setRoles(new ArrayList<>());
        AccountRepository accountRepository = mock(AccountRepository.class);
        when(accountRepository.save(Mockito.<Account>any())).thenReturn(account2);
        when(accountRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult);
        RoleRepository roleRepository = mock(RoleRepository.class);
        ModelMapper modelMapper = new ModelMapper();
        AccountService accountService = new AccountService(accountRepository, roleRepository, modelMapper,
                new BCryptPasswordEncoder());
        UUID id = UUID.randomUUID();

        HashSet<String> roles = new HashSet<>();
        roles.add("foo");
        AccountUpdationRequest request = AccountUpdationRequest.builder()
                .email("jane.doe@example.org")
                .fullName("Dr Jane Doe")
                .password("iloveyou")
                .phone("6625550144")
                .roles(roles)
                .build();

        // Act
        AccountResponse actualUpdateAccountResult = accountService.updateAccount(id, request);

        // Assert
        verify(accountRepository).findById(isA(UUID.class));
        verify(accountRepository).save(isA(Account.class));
        assertEquals("1970-01-01", actualUpdateAccountResult.getDob().toString());
        assertEquals("6625550144", actualUpdateAccountResult.getPhone());
        assertEquals("Dr Jane Doe", actualUpdateAccountResult.getFullName());
        assertEquals("jane.doe@example.org", actualUpdateAccountResult.getEmail());
        assertNull(actualUpdateAccountResult.getAddress());
        assertEquals(2, actualUpdateAccountResult.getId().variant());
    }

    /**
     * Method under test:
     * {@link AccountService#updateAccount(UUID, AccountUpdationRequest)}
     */
    @Test
    void testUpdateAccount9() throws Exception {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

        // Arrange
        Role role = new Role();
        role.setAccounts(new ArrayList<>());
        role.setCreatedDate(LocalDate.of(1970, 1, 1));
        role.setId(UUID.randomUUID());
        role.setModifiedDate(LocalDate.of(1970, 1, 1));
        role.setName("com.ducnt.chillshaker.model.Role");

        ArrayList<Role> roles = new ArrayList<>();
        roles.add(role);

        Account account = new Account();
        account.setAddress("42 Main St");
        account.setCreatedDate(LocalDate.of(1970, 1, 1));
        account.setDob(LocalDate.of(1970, 1, 1));
        account.setEmail("jane.doe@example.org");
        account.setFullName("Dr Jane Doe");
        account.setId(UUID.randomUUID());
        account.setModifiedDate(LocalDate.of(1970, 1, 1));
        account.setPassword("iloveyou");
        account.setPhone("6625550144");
        account.setRoles(roles);
        Optional<Account> ofResult = Optional.of(account);

        Account account2 = new Account();
        account2.setAddress("42 Main St");
        account2.setCreatedDate(LocalDate.of(1970, 1, 1));
        account2.setDob(LocalDate.of(1970, 1, 1));
        account2.setEmail("jane.doe@example.org");
        account2.setFullName("Dr Jane Doe");
        account2.setId(UUID.randomUUID());
        account2.setModifiedDate(LocalDate.of(1970, 1, 1));
        account2.setPassword("iloveyou");
        account2.setPhone("6625550144");
        account2.setRoles(new ArrayList<>());
        AccountRepository accountRepository = mock(AccountRepository.class);
        when(accountRepository.save(Mockito.<Account>any())).thenReturn(account2);
        when(accountRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult);
        RoleRepository roleRepository = mock(RoleRepository.class);
        ModelMapper modelMapper = new ModelMapper();
        AccountService accountService = new AccountService(accountRepository, roleRepository, modelMapper,
                new BCryptPasswordEncoder());
        UUID id = UUID.randomUUID();

        HashSet<String> roles2 = new HashSet<>();
        roles2.add("foo");
        AccountUpdationRequest request = AccountUpdationRequest.builder()
                .email("jane.doe@example.org")
                .fullName("Dr Jane Doe")
                .password("iloveyou")
                .phone("6625550144")
                .roles(roles2)
                .build();

        // Act
        AccountResponse actualUpdateAccountResult = accountService.updateAccount(id, request);

        // Assert
        verify(accountRepository).findById(isA(UUID.class));
        verify(accountRepository).save(isA(Account.class));
        assertEquals("1970-01-01", actualUpdateAccountResult.getDob().toString());
        assertEquals("6625550144", actualUpdateAccountResult.getPhone());
        assertEquals("Dr Jane Doe", actualUpdateAccountResult.getFullName());
        assertEquals("jane.doe@example.org", actualUpdateAccountResult.getEmail());
        assertNull(actualUpdateAccountResult.getAddress());
        assertEquals(2, actualUpdateAccountResult.getId().variant());
    }

    /**
     * Method under test:
     * {@link AccountService#updateAccount(UUID, AccountUpdationRequest)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testUpdateAccount10() throws Exception {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Reason: Failed to create Spring context.
        //   Attempt to initialize test context failed with
        //   java.lang.IllegalStateException: ApplicationContext failure threshold (1) exceeded: skipping repeated attempt to load context for [MergedContextConfiguration@60b1e246 testClass = com.ducnt.chillshaker.service.implement.DiffblueFakeClass236, locations = [], classes = [com.ducnt.chillshaker.service.implement.AccountService, org.springframework.security.crypto.password.PasswordEncoder], contextInitializerClasses = [], activeProfiles = [], propertySourceDescriptors = [], propertySourceProperties = [], contextCustomizers = [org.springframework.boot.test.context.filter.ExcludeFilterContextCustomizer@1765cb2e, org.springframework.boot.test.json.DuplicateJsonObjectContextCustomizerFactory$DuplicateJsonObjectContextCustomizer@67d9dde7, org.springframework.boot.test.mock.mockito.MockitoContextCustomizer@46708f0f, org.springframework.boot.test.web.reactor.netty.DisableReactorResourceFactoryGlobalResourcesContextCustomizerFactory$DisableReactorResourceFactoryGlobalResourcesContextCustomizerCustomizer@78a88d23, org.springframework.boot.test.autoconfigure.OnFailureConditionReportContextCustomizerFactory$OnFailureConditionReportContextCustomizer@33335f2b, org.springframework.boot.test.autoconfigure.actuate.observability.ObservabilityContextCustomizerFactory$DisableObservabilityContextCustomizer@1f, org.springframework.boot.test.autoconfigure.properties.PropertyMappingContextCustomizer@0, org.springframework.boot.test.autoconfigure.web.servlet.WebDriverContextCustomizer@620967ba], contextLoader = org.springframework.test.context.support.DelegatingSmartContextLoader, parent = null]
        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:145)
        //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:130)
        //       at java.base/java.util.Optional.map(Optional.java:260)
        //   See https://diff.blue/R026 to resolve this issue.

        // Arrange
        UUID id = UUID.randomUUID();

        // Act
        accountService.updateAccount(id, new AccountUpdationRequest());
    }

    /**
     * Method under test: {@link AccountService#deleteAccount(UUID)}
     */
    @Test
    void testDeleteAccount() throws Exception {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

        // Arrange
        Account account = new Account();
        account.setAddress("42 Main St");
        account.setCreatedDate(LocalDate.of(1970, 1, 1));
        account.setDob(LocalDate.of(1970, 1, 1));
        account.setEmail("jane.doe@example.org");
        account.setFullName("Dr Jane Doe");
        account.setId(UUID.randomUUID());
        account.setModifiedDate(LocalDate.of(1970, 1, 1));
        account.setPassword("iloveyou");
        account.setPhone("6625550144");
        account.setRoles(new ArrayList<>());
        Optional<Account> ofResult = Optional.of(account);
        AccountRepository accountRepository = mock(AccountRepository.class);
        doNothing().when(accountRepository).delete(Mockito.<Account>any());
        when(accountRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult);
        RoleRepository roleRepository = mock(RoleRepository.class);
        ModelMapper modelMapper = new ModelMapper();
        AccountService accountService = new AccountService(accountRepository, roleRepository, modelMapper,
                new BCryptPasswordEncoder());

        // Act
        boolean actualDeleteAccountResult = accountService.deleteAccount(UUID.randomUUID());

        // Assert
        verify(accountRepository).delete(isA(Account.class));
        verify(accountRepository).findById(isA(UUID.class));
        assertTrue(actualDeleteAccountResult);
    }

    /**
     * Method under test: {@link AccountService#deleteAccount(UUID)}
     */
    @Test
    void testDeleteAccount2() throws Exception {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

        // Arrange
        Account account = new Account();
        account.setAddress("42 Main St");
        account.setCreatedDate(LocalDate.of(1970, 1, 1));
        account.setDob(LocalDate.of(1970, 1, 1));
        account.setEmail("jane.doe@example.org");
        account.setFullName("Dr Jane Doe");
        account.setId(UUID.randomUUID());
        account.setModifiedDate(LocalDate.of(1970, 1, 1));
        account.setPassword("iloveyou");
        account.setPhone("6625550144");
        account.setRoles(new ArrayList<>());
        Optional<Account> ofResult = Optional.of(account);
        AccountRepository accountRepository = mock(AccountRepository.class);
        doThrow(new CustomException(ErrorResponse.FULLNAME_INVALID)).when(accountRepository).delete(Mockito.<Account>any());
        when(accountRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult);
        RoleRepository roleRepository = mock(RoleRepository.class);
        ModelMapper modelMapper = new ModelMapper();
        AccountService accountService = new AccountService(accountRepository, roleRepository, modelMapper,
                new BCryptPasswordEncoder());

        // Act and Assert
        assertThrows(Exception.class, () -> accountService.deleteAccount(UUID.randomUUID()));
        verify(accountRepository).delete(isA(Account.class));
        verify(accountRepository).findById(isA(UUID.class));
    }

    /**
     * Method under test: {@link AccountService#deleteAccount(UUID)}
     */
    @Test
    void testDeleteAccount3() throws Exception {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

        // Arrange
        Account account = new Account();
        account.setAddress("42 Main St");
        account.setCreatedDate(LocalDate.of(1970, 1, 1));
        account.setDob(LocalDate.of(1970, 1, 1));
        account.setEmail("jane.doe@example.org");
        account.setFullName("Dr Jane Doe");
        account.setId(UUID.randomUUID());
        account.setModifiedDate(LocalDate.of(1970, 1, 1));
        account.setPassword("iloveyou");
        account.setPhone("6625550144");
        account.setRoles(new ArrayList<>());
        Optional<Account> ofResult = Optional.of(account);
        AccountRepository accountRepository = mock(AccountRepository.class);
        doThrow(new NotFoundException("An error occurred")).when(accountRepository).delete(Mockito.<Account>any());
        when(accountRepository.findById(Mockito.<UUID>any())).thenReturn(ofResult);
        RoleRepository roleRepository = mock(RoleRepository.class);
        ModelMapper modelMapper = new ModelMapper();
        AccountService accountService = new AccountService(accountRepository, roleRepository, modelMapper,
                new BCryptPasswordEncoder());

        // Act and Assert
        assertThrows(NotFoundException.class, () -> accountService.deleteAccount(UUID.randomUUID()));
        verify(accountRepository).delete(isA(Account.class));
        verify(accountRepository).findById(isA(UUID.class));
    }

    /**
     * Method under test: {@link AccountService#deleteAccount(UUID)}
     */
    @Test
    void testDeleteAccount4() throws Exception {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

        // Arrange
        AccountRepository accountRepository = mock(AccountRepository.class);
        Optional<Account> emptyResult = Optional.empty();
        when(accountRepository.findById(Mockito.<UUID>any())).thenReturn(emptyResult);
        RoleRepository roleRepository = mock(RoleRepository.class);
        ModelMapper modelMapper = new ModelMapper();
        AccountService accountService = new AccountService(accountRepository, roleRepository, modelMapper,
                new BCryptPasswordEncoder());

        // Act and Assert
        assertThrows(NotFoundException.class, () -> accountService.deleteAccount(UUID.randomUUID()));
        verify(accountRepository).findById(isA(UUID.class));
    }

    /**
     * Method under test: {@link AccountService#deleteAccount(UUID)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testDeleteAccount5() throws Exception {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Reason: Failed to create Spring context.
        //   Attempt to initialize test context failed with
        //   java.lang.IllegalStateException: ApplicationContext failure threshold (1) exceeded: skipping repeated attempt to load context for [MergedContextConfiguration@4426a895 testClass = com.ducnt.chillshaker.service.implement.DiffblueFakeClass221, locations = [], classes = [com.ducnt.chillshaker.service.implement.AccountService, org.springframework.security.crypto.password.PasswordEncoder], contextInitializerClasses = [], activeProfiles = [], propertySourceDescriptors = [], propertySourceProperties = [], contextCustomizers = [org.springframework.boot.test.context.filter.ExcludeFilterContextCustomizer@1765cb2e, org.springframework.boot.test.json.DuplicateJsonObjectContextCustomizerFactory$DuplicateJsonObjectContextCustomizer@67d9dde7, org.springframework.boot.test.mock.mockito.MockitoContextCustomizer@46708f0f, org.springframework.boot.test.web.reactor.netty.DisableReactorResourceFactoryGlobalResourcesContextCustomizerFactory$DisableReactorResourceFactoryGlobalResourcesContextCustomizerCustomizer@78a88d23, org.springframework.boot.test.autoconfigure.OnFailureConditionReportContextCustomizerFactory$OnFailureConditionReportContextCustomizer@33335f2b, org.springframework.boot.test.autoconfigure.actuate.observability.ObservabilityContextCustomizerFactory$DisableObservabilityContextCustomizer@1f, org.springframework.boot.test.autoconfigure.properties.PropertyMappingContextCustomizer@0, org.springframework.boot.test.autoconfigure.web.servlet.WebDriverContextCustomizer@620967ba], contextLoader = org.springframework.test.context.support.DelegatingSmartContextLoader, parent = null]
        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:145)
        //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:130)
        //       at java.base/java.util.Optional.map(Optional.java:260)
        //   See https://diff.blue/R026 to resolve this issue.

        // Arrange and Act
        accountService.deleteAccount(UUID.randomUUID());
    }
}
