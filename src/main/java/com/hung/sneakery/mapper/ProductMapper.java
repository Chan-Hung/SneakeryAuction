package com.hung.sneakery.mapper;

import com.hung.sneakery.mapper.base.AbstractModelMapper;
import com.hung.sneakery.dto.ProductDTO;
import com.hung.sneakery.entities.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper extends AbstractModelMapper<Product, ProductDTO> {
}
