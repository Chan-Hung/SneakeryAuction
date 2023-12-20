package com.hung.sneakery.controller;

import com.hung.sneakery.dto.OrderDTO;
import com.hung.sneakery.dto.request.OrderRequest;
import com.hung.sneakery.service.OrderService;
import io.swagger.annotations.Api;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@Api(tags = "Order APIs")
@CrossOrigin(origins = {"https://sneakery-kietdarealist.vercel.app/", "http://localhost:3000", "https://sneakery.vercel.app/"})
@RequestMapping("/orders")
public class OrderController {

    @Resource
    private OrderService orderService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public Page<OrderDTO> getAll(final Pageable pageable) {
        return orderService.getAll(pageable);
    }

    @GetMapping("/users/{id}")
    public Page<OrderDTO> getByUser(@PathVariable final Long id, final Pageable pageable) {
        return orderService.getByUser(id, pageable);
    }

    @PutMapping("/{id}")
    public OrderDTO update(@PathVariable final Long id, @Valid @RequestBody final OrderRequest request) {
        return orderService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public OrderDTO delete(@PathVariable final Long id) {
        return orderService.delete(id);
    }
}
