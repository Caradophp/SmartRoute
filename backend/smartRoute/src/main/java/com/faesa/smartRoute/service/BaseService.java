package com.faesa.smartRoute.service;


/**
 * @author luciano
 *
 * Essa classe tem como objetivo forçar todo e qualquer service
 * a ter os métodos básicos de conversão para converter entidades para DTOs e vice-versa
 * */
public abstract class BaseService {

    public abstract <E, D> D convertToDto(E entity, Class<D> dClass);

    public abstract <E, D> E convertToEntity(D dto, Class<E> dClass);

}
