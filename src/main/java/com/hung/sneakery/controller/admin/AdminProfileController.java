package com.hung.sneakery.controller.admin;

import com.hung.sneakery.dto.UserDTO;
import com.hung.sneakery.service.ProfileService;
import io.swagger.annotations.Api;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Api(tags = "Profile APIs")
@CrossOrigin(origins = {"http://localhost:3000", "https://aunction-react-js.vercel.app/"})
@RequestMapping("/admin/profile")
public class AdminProfileController {

    @Resource
    private ProfileService profileService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping()
    public List<UserDTO> getAll() {
        return profileService.getAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{userId}")
    public UserDTO getOne(@PathVariable Long userId) {
        return profileService.getOne(userId);
    }
}
