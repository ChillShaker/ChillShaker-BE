package com.ducnt.chillshaker.config.application;

import com.ducnt.chillshaker.enums.AccountStatusEnum;
import com.ducnt.chillshaker.enums.RoleEnum;
import com.ducnt.chillshaker.model.Account;
import com.ducnt.chillshaker.model.Role;
import com.ducnt.chillshaker.repository.AccountRepository;
import com.ducnt.chillshaker.repository.DrinkCategoryRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {

    RedisConnectionFactory redisConnectionFactory;
    PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner(AccountRepository accountRepository, DrinkCategoryRepository drinkCategoryRepository) {
        return args -> {
            redisConnectionFactory.getConnection().serverCommands().flushAll();

          if(accountRepository.findByEmail("admin@chillshaker.com").isEmpty()) {
              Role adminRole = Role.builder().name(RoleEnum.ADMIN.name()).build();
              Role customerRole = Role.builder().name(RoleEnum.CUSTOMER.name()).build();
              Collection<Role> adminRoles = new HashSet<>();
              adminRoles.add(adminRole);

              Account adminAccount = Account
                      .builder()
                      .email("admin@chillshaker.com")
                      .password(passwordEncoder.encode("admin"))
                      .roles(adminRoles)
                      .status(AccountStatusEnum.ACTIVE)
                      .build();

              Collection<Role> customerRoles = new HashSet<>();
              customerRoles.add(customerRole);
              Account customer1Account = Account
                      .builder()
                      .email("chillshakertest@tempumail.show")
                      .password(passwordEncoder.encode("string"))
                      .fullName("customer1")
                      .phone("0987654321")
                      .dob(LocalDate.of(2002, 12, 1))
                      .image("")
                      .roles(customerRoles)
                      .status(AccountStatusEnum.ACTIVE)
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
