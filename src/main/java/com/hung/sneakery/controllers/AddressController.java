package com.hung.sneakery.controllers;

import com.hung.sneakery.data.models.dto.AddressDTO;
import com.hung.sneakery.data.models.dto.response.BaseResponse;
import com.hung.sneakery.data.models.entities.City;
import com.hung.sneakery.data.models.entities.District;
import com.hung.sneakery.data.models.entities.Ward;
import com.hung.sneakery.data.remotes.repositories.BidHistoryRepository;
import com.hung.sneakery.data.remotes.repositories.CityRepository;
import com.hung.sneakery.data.remotes.repositories.DistrictRepository;
import com.hung.sneakery.data.remotes.repositories.WardRepository;
import com.hung.sneakery.data.remotes.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Tuple;
import java.math.BigInteger;
import java.util.List;

@RestController
@CrossOrigin(origins = {"https://sneakery-kietdarealist.vercel.app/", "http://localhost:3000"})
@RequestMapping("/api/address")
public class AddressController {
    @Autowired
    CityRepository cityRepository;

    @Autowired
    DistrictRepository districtRepository;

    @Autowired
    WardRepository wardRepository;

    @Autowired
    BidHistoryRepository bidHistoryRepository;

    @Autowired
    AddressService addressService;

    @GetMapping("/test")
    public List<Ward> getAllWard() {
        Tuple winner = bidHistoryRepository.getWinner(100L);
        BigInteger buyerId = winner.get("buyerId", BigInteger.class);
        BigInteger priceWin = winner.get("priceWin", BigInteger.class);
        System.out.println(buyerId);
        System.out.println(priceWin);

        return wardRepository.findAll();
    }

    @GetMapping("/cities")
    public List<City> getAllCities() {
        return cityRepository.findAll();
    }

    @GetMapping("/districts")
    public List<District> getAllDistricts() {
        return districtRepository.findAll();
    }

    @GetMapping("/districts/{id}")
    public List<Ward> getWardsInDistricts(@PathVariable Long id) {
        return wardRepository.findByDistrict_Id(id);
    }

    @GetMapping("/wards")
    public List<Ward> getAllWards() {
        return wardRepository.findAll();
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BaseResponse> create(@RequestBody AddressDTO addressDTO) {
        try {
            return ResponseEntity
                    .ok(addressService.create(addressDTO));
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new BaseResponse(false, e.getMessage()));
        }
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BaseResponse> update(@RequestBody AddressDTO addressDTO) {
        try {
            return ResponseEntity
                    .ok(addressService.update(addressDTO));
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new BaseResponse(false, e.getMessage()));
        }
    }

    @DeleteMapping("/delete/{addressId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BaseResponse> delete(@PathVariable Long addressId) {
        try {
            return ResponseEntity
                    .ok(addressService.delete(addressId));
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new BaseResponse(false, e.getMessage()));
        }
    }

    @GetMapping("/get/{addressId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BaseResponse> getOne(@PathVariable Long addressId) {
        try {
            return ResponseEntity
                    .ok(addressService.getOne(addressId));
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new BaseResponse(false, e.getMessage()));
        }
    }

    @GetMapping("/get")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BaseResponse> getOneByUser() {
        try {
            return ResponseEntity
                    .ok(addressService.getOneByUser());
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new BaseResponse(false, e.getMessage()));
        }
    }

    @GetMapping("/get_all")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BaseResponse> getAll() {
        try {
            return ResponseEntity
                    .ok(addressService.getAll());
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new BaseResponse(false, e.getMessage()));
        }
    }
}
