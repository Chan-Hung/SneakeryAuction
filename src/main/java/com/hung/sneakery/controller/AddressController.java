package com.hung.sneakery.controller;

import com.hung.sneakery.model.City;
import com.hung.sneakery.model.District;
import com.hung.sneakery.model.Ward;
import com.hung.sneakery.repository.BidHistoryRepository;
import com.hung.sneakery.repository.CityRepository;
import com.hung.sneakery.repository.DistrictRepository;
import com.hung.sneakery.repository.WardRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

}
