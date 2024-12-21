package com.ducnt.chillshaker.config;

import com.ducnt.chillshaker.model.Account;
import com.ducnt.chillshaker.model.Role;
import com.ducnt.chillshaker.repository.AccountRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {

    PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner(AccountRepository accountRepository) {
        return args -> {
          if(accountRepository.findByEmail("admin@chillshaker.com").isEmpty()) {
              Role adminRole = Role.builder().name(Role.RoleName.ADMIN.name()).build();
              Role customerRole = Role.builder().name(Role.RoleName.USER.name()).build();
              Collection<Role> adminRoles = new HashSet<>();
              adminRoles.add(adminRole);

              Account adminAccount = Account
                      .builder()
                      .email("admin@chillshaker.com")
                      .password(passwordEncoder.encode("admin"))
                      .roles(adminRoles)
                      .build();

              Collection<Role> customerRoles = new HashSet<>();
              customerRoles.add(customerRole);
              Account customer1Account = Account
                      .builder()
                      .email("customer1@gmail.com")
                      .password(passwordEncoder.encode("string"))
                      .roles(customerRoles)
                      .build();

              var accounts = new ArrayList<Account>();
              accounts.add(customer1Account);
              accounts.add(adminAccount);

              accountRepository.saveAll(accounts);
              log.warn("Admin account created with email: admin@chillshaker.com and password: admin, please change it");
          }
        };
    }
}
