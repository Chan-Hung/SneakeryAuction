package com.hung.sneakery.data.mappers;

import com.hung.sneakery.data.mappers.base.AbstractModelMapper;
import com.hung.sneakery.data.models.dto.ProductDTO;
import com.hung.sneakery.data.models.entities.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper extends AbstractModelMapper<Product, ProductDTO> {
}
