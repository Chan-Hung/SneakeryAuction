package com.hung.sneakery.controller.admin;

import com.hung.sneakery.dto.response.BaseResponse;
import com.hung.sneakery.service.ProfileService;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Api(tags = "Profile APIs")
@CrossOrigin(origins = {"http://localhost:3000", "https://aunction-react-js.vercel.app/"})
@RequestMapping("/admin/profile")
public class AdminProfileController {

    @Resource
    ProfileService profileService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping()
    public ResponseEntity<BaseResponse> getAll() {
        try {
            return ResponseEntity
                    .ok(profileService.getAll());
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new BaseResponse(false, e.getMessage()));
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{userId}")
    public ResponseEntity<BaseResponse> getOne(@PathVariable Long userId) {
        try {
            return ResponseEntity
                    .ok(profileService.getOne(userId));
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new BaseResponse(false, e.getMessage()));
        }
    }
}
