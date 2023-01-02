package com.hung.sneakery.data.mappers;

import com.hung.sneakery.data.mappers.base.AbstractModelMapper;
import com.hung.sneakery.data.models.dto.AddressDTO;
import com.hung.sneakery.data.models.entities.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper extends AbstractModelMapper<Address, AddressDTO> {
}
