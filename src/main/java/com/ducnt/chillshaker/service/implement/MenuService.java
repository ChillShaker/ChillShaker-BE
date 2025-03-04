package com.ducnt.chillshaker.service.implement;

import com.ducnt.chillshaker.dto.request.menu.MenuCreationRequest;
import com.ducnt.chillshaker.dto.request.menu.MenuUpdateRequest;
import com.ducnt.chillshaker.dto.response.menu.MenuResponse;
import com.ducnt.chillshaker.exception.CustomException;
import com.ducnt.chillshaker.exception.ErrorResponse;
import com.ducnt.chillshaker.exception.NotFoundException;
import com.ducnt.chillshaker.model.Menu;
import com.ducnt.chillshaker.repository.GenericSpecification;
import com.ducnt.chillshaker.repository.MenuRepository;
import com.ducnt.chillshaker.service.thirdparty.CloudinaryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MenuService implements com.ducnt.chillshaker.service.interfaces.IMenuService {
    MenuRepository menuRepository;
    ModelMapper modelMapper;
    CloudinaryService cloudinaryService;
    GenericSpecification<Menu> genericSpecification;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public MenuResponse createMenu(MenuCreationRequest request) {
        try {
            Menu menu = modelMapper.map(request, Menu.class);
            List<String> imageUrls = cloudinaryService.uploadFiles(request.getFiles());
            menu.setImages(String.join(", ", imageUrls));

            menuRepository.save(menu);
            return modelMapper.map(menu, MenuResponse.class);
        } catch (Exception ex) {
            throw new CustomException(ErrorResponse.INTERNAL_SERVER);
        }
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public MenuResponse updateMenu(UUID id, MenuUpdateRequest request) {
        try {
            Menu menu = menuRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Menu is not existed"));
            modelMapper.map(menu, request);
            List<String> imageUrls = cloudinaryService.updateFiles(request.getOldUrls(), request.getNewfiles());
            menu.setImages(String.join(", ", imageUrls));
            return modelMapper.map(menu, MenuResponse.class);
        } catch (NotFoundException ex) {
            throw new NotFoundException(ex.getMessage());
        } catch (Exception ex) {
            throw new CustomException(ErrorResponse.INTERNAL_SERVER);
        }
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public boolean deleteMenu(UUID id) {
        try {
            Menu menu = menuRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Menu is not existed"));
            menu.setStatus(false);
            menuRepository.save(menu);
            return true;
        } catch (NotFoundException ex) {
            throw new NotFoundException(ex.getMessage());
        } catch (Exception ex) {
            throw new CustomException(ErrorResponse.INTERNAL_SERVER);
        }
    }

    @Override
    public Page<MenuResponse> getAllMenu(
            String q,
            String includeProperties,
            String attribute,
            Integer pageIndex,
            Integer pageSize,
            String sort
    ) {
        try {
            PageRequest pageRequest = PageRequest.of(
                    pageIndex > 0 ? pageIndex - 1 : 0,
                    pageSize,
                    sort.equals("desc") ? Sort.by(Sort.Direction.DESC, attribute) : Sort.by(Sort.Direction.ASC, attribute)
            );

            //var filters = genericSpecification.getFilters(q, includeProperties, attribute);

            long totalOfElement = menuRepository.count();

            Page<Menu> menuPage = menuRepository.findAllByStatusTrue(pageRequest);

            List<MenuResponse> menuResponses = menuPage.getContent().stream()
                    .map(element -> modelMapper.map(element, MenuResponse.class)).toList();

            return new PageImpl<>(menuResponses, menuPage.getPageable(), totalOfElement);
        } catch (Exception ex) {
            throw new CustomException(ErrorResponse.INTERNAL_SERVER);
        }
    }

    @Override
    public MenuResponse getMenuById(UUID id) {
        try {
            Menu menu = menuRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Menu is not existed"));

            return modelMapper.map(menu, MenuResponse.class);
        } catch (NotFoundException ex) {
            throw new NotFoundException(ex.getMessage());
        } catch (Exception ex) {
            throw new CustomException(ErrorResponse.INTERNAL_SERVER);
        }
    }
}
