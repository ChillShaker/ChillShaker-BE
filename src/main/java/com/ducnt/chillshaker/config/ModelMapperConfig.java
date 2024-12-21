package com.ducnt.chillshaker.config;

import com.ducnt.chillshaker.dto.request.AccountCreationRequest;
import com.ducnt.chillshaker.model.Account;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(new PropertyMap<AccountCreationRequest, Account>() {
            @Override
            protected void configure() {

            }
        });
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);
        return  modelMapper;
    }
}
