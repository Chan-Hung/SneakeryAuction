package com.hung.sneakery.data.remotes.services;

import com.hung.sneakery.data.models.dto.AddressDTO;
import com.hung.sneakery.data.models.dto.request.AddressCreateRequest;
import com.hung.sneakery.data.models.dto.response.BaseResponse;
import com.hung.sneakery.data.models.dto.response.DataResponse;

import java.util.List;

public interface AddressService {
    BaseResponse create(AddressCreateRequest addressCreateRequest);
    BaseResponse update(AddressDTO addressDTO);
    BaseResponse delete(Long addressId);

    DataResponse<AddressDTO> getOne(Long addressId);
    DataResponse<AddressDTO> getOneByUser();
    DataResponse<List<AddressDTO>> getAll();

}
