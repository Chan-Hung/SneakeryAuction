package com.hung.sneakery.controller;

import com.hung.sneakery.model.Transport;
import com.hung.sneakery.repository.TransportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/api/v1")
public class ProductController {

    @Autowired
    public TransportRepository transportRepository;

    @PostMapping("/transport")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Transport createTransport(@RequestBody Transport trans){
        return transportRepository.save(trans);
    }
}
