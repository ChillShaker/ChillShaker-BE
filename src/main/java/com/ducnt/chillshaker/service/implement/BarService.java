package com.ducnt.chillshaker.service.implement;

import com.ducnt.chillshaker.dto.request.bar.BarUpdationRequest;
import com.ducnt.chillshaker.dto.response.bar.BarResponse;
import com.ducnt.chillshaker.exception.NotFoundException;
import com.ducnt.chillshaker.repository.BarRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class BarService {
    BarRepository barRepository;
    ModelMapper modelMapper;

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public BarResponse updateBar(UUID id, BarUpdationRequest request) {
        var bar = barRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Bar not found"));
        modelMapper.map(request, bar);

        bar.setModifiedDate(LocalDate.now());
        barRepository.save(bar);
        return  modelMapper.map(bar, BarResponse.class);
    }

    @Transactional
    public BarResponse getBarById(UUID id) {
        var bar = barRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Bar not found"));

        return  modelMapper.map(bar, BarResponse.class);
    }
}
