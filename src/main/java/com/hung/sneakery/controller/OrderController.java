package com.hung.sneakery.controller;

import com.hung.sneakery.dto.OrderDTO;
import com.hung.sneakery.service.OrderService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@CrossOrigin(origins = {"https://sneakery-kietdarealist.vercel.app/", "http://localhost:3000", "https://sneakery.vercel.app/"})
@RequestMapping("/orders")
public class OrderController {

    @Resource
    private OrderService orderService;

    @GetMapping()
    public List<OrderDTO> getAllByUser() {
        return orderService.getAllByUser();
    }
}
