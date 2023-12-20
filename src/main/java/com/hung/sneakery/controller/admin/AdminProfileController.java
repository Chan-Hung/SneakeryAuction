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
@CrossOrigin(origins = {"http://localhost:3000", "https://aunction-react-js.vercel.app/"})
@RequestMapping("/admin/profiles")
public class AdminProfileController {

    @Resource
    private ProfileService profileService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping()
    public Page<UserDTO> getAll(final Pageable pageable) {
        return profileService.getAll(pageable);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public UserDTO getOne(@PathVariable final Long id) {
        return profileService.getOne(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public UserDTO update(@PathVariable final Long id, @RequestBody final UserDTO userDTO) {
        return profileService.update(id, userDTO);
    }
}
