package com.hung.sneakery.controllers.admin;

import com.hung.sneakery.data.models.dto.response.BaseResponse;
import com.hung.sneakery.data.remotes.services.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "https://aunction-react-js.vercel.app/"} )
@RequestMapping("/api/admin/profile")
public class AdminProfileController {
    @Autowired
    ProfileService profileService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get_all")
    public ResponseEntity<BaseResponse> getAll(){
        try{
            return ResponseEntity
                    .ok(profileService.getAll());
        }catch (RuntimeException e){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new BaseResponse(false, e.getMessage()));
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get/{userId}")
    public ResponseEntity<BaseResponse> getOne(@PathVariable Long userId){
        try{
            return ResponseEntity
                    .ok(profileService.getOne(userId));
        }catch (RuntimeException e){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new BaseResponse(false, e.getMessage()));
        }
    }

}
