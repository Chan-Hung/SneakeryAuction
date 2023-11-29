package com.hung.sneakery.controller.admin;

import com.hung.sneakery.dto.response.RevenueResponse;
import com.hung.sneakery.service.RevenueService;
import io.swagger.annotations.Api;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Api(tags = "Revenue APIs")
@CrossOrigin(originPatterns = {"http://localhost:3000", "https://aunction-react-js.vercel.app/"})
@RequestMapping("/admin/revenue")
public class AdminRevenueController {

    @Resource
    private RevenueService revenueService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping()
    public RevenueResponse getAllByAdmin() {
        return revenueService.getRevenue();
    }
}
