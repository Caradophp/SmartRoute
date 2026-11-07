package com.faesa.smartRoute.service;

import com.faesa.smartRoute.dto.request.DriverRequestDto;
import com.faesa.smartRoute.exceptions.RecordNotFoundException;
import com.faesa.smartRoute.model.Driver;
import com.faesa.smartRoute.model.User;
import com.faesa.smartRoute.repository.DriverRepository;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

@Service
public class DriverService extends BaseService {

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private UserService userService;

    private final String[] LINCENSE_LEVELS_ALLOWED = {"A", "B", "C", "AB", "AC","ABC"};

    public void saveDriver(DriverRequestDto driverRequestDto) {
        String licenceLevel = driverRequestDto.driverLicenceLevel();
        UUID userId = UUID.fromString(driverRequestDto.userId());
        User userById = userService.findById(userId);

        if (Objects.isNull(userById)) {
            throw new RecordNotFoundException("Usuário informado é inválido");
        }

        if (!checkIfLicenceLevelIsValid(licenceLevel)) {
            throw new IllegalArgumentException("Tipo de carteira de motorista invalido. Tipos válidos: "
                    + ArrayUtils.toString(LINCENSE_LEVELS_ALLOWED).replaceAll("[{}]", ""));
        }

        Driver driver
                = new Driver(licenceLevel, userById);

        driverRepository.save(driver);

    }

    @Override
    public <E, D> D convertToDto(E entity, Class<D> dClass) {
        Driver driver = (Driver) entity;
        DriverRequestDto dto
                = new DriverRequestDto(driver.getDriverLicenceLevel(), String.valueOf(driver.getDriver().getId()));

        return (D) dto;
    }

    @Override
    public <E, D> E convertToEntity(D dto, Class<E> dClass) {

        DriverRequestDto driverDto = (DriverRequestDto) dto;

        Driver driver = new Driver();
        driver.setDriverLicenceLevel(driverDto.driverLicenceLevel());

        User byId = userService.findById(UUID.fromString(driverDto.userId()));

        driver.setDriver(byId);

        return (E) driver;
    }

    private boolean checkIfLicenceLevelIsValid(String licenceLevel) {
        return Arrays.stream(LINCENSE_LEVELS_ALLOWED)
                .anyMatch(licenceLevel::equals);
    }
}
