package com.hung.sneakery.data.mappers.base;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class AbstractModelMapper <E,D>{
    @Autowired
    ModelMapper mapper;

    public D mapToDTO(E e, Class<D> d){ return e != null ? mapper.map(e,d) : null;}

    public E mapToEntity(D d, Class<E> e){ return d!= null ? mapper.map(d, e) : null;}

    public List<D> mapToDTOList(List<E> eList, Class<D> d){
        return eList != null ? eList.stream().map(e -> mapper.map(e,d)).collect(Collectors.toList()) : null;
    }
}
