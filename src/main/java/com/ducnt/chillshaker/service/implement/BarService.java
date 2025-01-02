package com.ducnt.chillshaker.service.implement;

import com.ducnt.chillshaker.dto.request.bar.BarUpdateRequest;
import com.ducnt.chillshaker.dto.response.bar.BarResponse;
import com.ducnt.chillshaker.exception.CustomException;
import com.ducnt.chillshaker.exception.ErrorResponse;
import com.ducnt.chillshaker.exception.NotFoundException;
import com.ducnt.chillshaker.repository.BarRepository;
import com.ducnt.chillshaker.service.thirdparty.CloudinaryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class BarService {
    BarRepository barRepository;
    ModelMapper modelMapper;
    CloudinaryService cloudinaryService;

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public BarResponse updateBar(UUID id, BarUpdateRequest request) {
        try {
            var bar = barRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Bar not found"));
            modelMapper.map(request, bar);

            bar.setModifiedDate(LocalDate.now());
            List<String> updatedImageUrls = cloudinaryService.updateFiles(request.getOldFileUrls(), request.getNewFiles());
            bar.setImage(String.join(", ", updatedImageUrls));

            barRepository.save(bar);
            return  modelMapper.map(bar, BarResponse.class);
        } catch (NotFoundException ex) {
            throw new NotFoundException(ex.getMessage());
        } catch (IOException ex) {
            throw new CustomException(ErrorResponse.IMAGE_FILE_INVALID);
        } catch (Exception ex) {
            throw new CustomException(ErrorResponse.INTERNAL_SERVER);
        }
    }

    @Transactional
    public BarResponse getBarById(UUID id) {
        var bar = barRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Bar not found"));

        return  modelMapper.map(bar, BarResponse.class);
    }
}
