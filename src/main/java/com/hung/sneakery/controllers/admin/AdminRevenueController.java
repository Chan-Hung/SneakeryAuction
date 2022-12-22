package com.hung.sneakery.controllers.admin;

import com.hung.sneakery.data.models.dto.response.BaseResponse;
import com.hung.sneakery.data.remotes.services.RevenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(originPatterns = {"http://localhost:3000", "https://aunction-react-js.vercel.app/"})
@RequestMapping("/api/admin/revenue")
public class AdminRevenueController {
    @Autowired
    RevenueService revenueService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get_all")
    public ResponseEntity<BaseResponse> getAllByAdmin(){
        try{
            return ResponseEntity
                    .ok(revenueService.getAllByAdmin());
        }catch (RuntimeException e){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new BaseResponse(false, e.getMessage()));
        }
    }
}
