package com.faesa.smartRoute.service;

public abstract class BaseService {

    public abstract <E, D> D convertToDto(E entity, Class<D> dClass);

    public abstract <E, D> E convertToEntity(D dto, Class<E> dClass);

}
