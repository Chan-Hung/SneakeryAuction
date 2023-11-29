package com.hung.sneakery.controller;

import com.hung.sneakery.dto.AddressDTO;
import com.hung.sneakery.dto.request.AddressRequest;
import com.hung.sneakery.service.AddressService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
@Api(tags = "Address APIs")
@CrossOrigin(origins = {"https://sneakery-kietdarealist.vercel.app/", "http://localhost:3000", "https://sneakery.vercel.app/", "https://aunction-react-js.vercel.app/"})
@RequestMapping("/addresses")
public class AddressController {

    @Resource
    private AddressService addressService;

    @GetMapping("/{id}")
    public AddressDTO getOne(@PathVariable Long id) {
        return addressService.getOne(id);
    }

    @GetMapping()
    public List<AddressDTO> getAll() {
        return addressService.getAll();
    }

    @PostMapping()
    public AddressDTO create(@Valid @RequestBody AddressRequest request) {
        return addressService.create(request);
    }

    @PutMapping("/{id}")
    public AddressDTO update(@PathVariable Long id, @Valid @RequestBody AddressRequest request) {
        return addressService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public AddressDTO delete(@PathVariable Long id) {
        return addressService.delete(id);
    }
}
