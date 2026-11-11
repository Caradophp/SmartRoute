package com.faesa.smartRoute.service;


import java.util.UUID;

/**
 * @author luciano
 *
 * Essa classe tem como objetivo forçar todo e qualquer service
 * a ter os métodos básicos de conversão para converter entidades para DTOs e vice-versa,
 * Além de fornecer métodos de conversão de ID
 * */
public abstract class BaseService {

    public abstract <E, D> D convertToDto(E entity, Class<D> dClass);

    public abstract <E, D> E convertToEntity(D dto, Class<E> dClass);

    protected UUID stringToUuid(String uuid) {
        return UUID.fromString(uuid);
    }

    protected String uuidToString(UUID uuid) {
        return String.valueOf(uuid);
    }

}
