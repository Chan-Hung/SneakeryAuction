package com.hung.sneakery.controllers;

import com.hung.sneakery.data.models.entities.City;
import com.hung.sneakery.data.models.entities.District;
import com.hung.sneakery.data.models.entities.Ward;
import com.hung.sneakery.data.models.dto.request.AddressRequest;
import com.hung.sneakery.data.models.dto.response.BaseResponse;
import com.hung.sneakery.data.remotes.repositories.BidHistoryRepository;
import com.hung.sneakery.data.remotes.repositories.CityRepository;
import com.hung.sneakery.data.remotes.repositories.DistrictRepository;
import com.hung.sneakery.data.remotes.repositories.WardRepository;
import com.hung.sneakery.data.remotes.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Tuple;
import java.math.BigInteger;
import java.util.List;

@RestController
@CrossOrigin(origins = {"https://sneakery-kietdarealist.vercel.app/","http://localhost:3000"})
@RequestMapping("/api/address")
public class AddressController {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private DistrictRepository districtRepository;

    @Autowired
    private WardRepository wardRepository;


    @Autowired
    BidHistoryRepository bidHistoryRepository;

    @Autowired
    AddressService addressService;

    @GetMapping("/test")
    public List<Ward> getAllWard(){
        Tuple winner = bidHistoryRepository.getWinner(100L);
        BigInteger buyerId = winner.get("buyerId", BigInteger.class);
        BigInteger priceWin = winner.get("priceWin", BigInteger.class);
        System.out.println(buyerId);
        System.out.println(priceWin);

        return wardRepository.findAll();
    }
    @GetMapping ("/cities")
    public List<City> getAllCities(){
        return cityRepository.findAll();
    }

    @GetMapping("/districts")
    public List<District> getAllDistricts(){
        return districtRepository.findAll();
    }

    @GetMapping("/districts/{id}")
    public List<Ward> getWardsInDistricts(@PathVariable Long id){
        return wardRepository.findByDistrict_Id(id);
    }

    @GetMapping("/wards")
    public List<Ward> getAllWards(){
        return wardRepository.findAll();
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BaseResponse> createAddress(@RequestBody AddressRequest addressRequest){
        try{
            return ResponseEntity
                    .ok(addressService.create(addressRequest));
        }
        catch(RuntimeException e){
            return ResponseEntity
                    .status(500)
                    .body(new BaseResponse(false, e.getMessage()));
        }
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BaseResponse> updateAddress(@RequestBody AddressRequest addressRequest){
        try{
            return ResponseEntity
                    .ok(addressService.update(addressRequest));
        }
        catch(RuntimeException e){
            return ResponseEntity
                    .status(500)
                    .body(new BaseResponse(false, e.getMessage()));
        }
    }
}
