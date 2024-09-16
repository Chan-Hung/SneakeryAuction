package com.hung.sneakery.controller.admin;

import com.hung.sneakery.dto.UserDTO;
import com.hung.sneakery.service.ProfileService;
import io.swagger.annotations.Api;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Api(tags = "Profile APIs")
@RequestMapping("/profiles")
@PreAuthorize("hasRole('ADMIN')")
public class ProfileController {

    @Resource
    private ProfileService profileService;

    @GetMapping
    public Page<UserDTO> getAll(final Pageable pageable) {
        return profileService.getAll(pageable);
    }

    @GetMapping("/{id}")
    public UserDTO getOne(@PathVariable final Long id) {
        return profileService.getOne(id);
    }

    @PutMapping("/{id}")
    public UserDTO update(@PathVariable final Long id, @RequestBody final UserDTO userDTO) {
        return profileService.update(id, userDTO);
    }
}
