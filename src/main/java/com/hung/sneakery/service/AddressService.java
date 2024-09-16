package com.hung.sneakery.service;

import com.hung.sneakery.dto.AddressDTO;
import com.hung.sneakery.dto.request.AddressRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AddressService {

    /**
     * Get Address
     *
     * @param id Long
     * @return AddressDTO
     */
    AddressDTO getOne(Long id);

    /**
     * Get all Address
     *
     * @param pageable Pageable
     * @return Page<AddressDTO>
     */
    Page<AddressDTO> getAll(Pageable pageable);

    /**
     * Create Address
     *
     * @param request AddressRequest
     * @return AddressDTO
     */
    AddressDTO create(AddressRequest request);

    /**
     * Update Address
     *
     * @param id      Long
     * @param request AddressRequest
     * @return AddressDTO
     */
    AddressDTO update(Long id, AddressRequest request);

    /**
     * Delete Address
     *
     * @param id Long
     * @return AddressDTO
     */
    AddressDTO delete(Long id);
}
