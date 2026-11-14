package com.faesa.smartRoute.service;

import com.faesa.smartRoute.dto.request.DriverRequestDto;
import com.faesa.smartRoute.dto.response.DriverResponseDto;
import com.faesa.smartRoute.exceptions.RecordNotFoundException;
import com.faesa.smartRoute.model.Driver;
import com.faesa.smartRoute.model.User;
import com.faesa.smartRoute.repository.DriverRepository;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class DriverService extends BaseService {

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private UserService userService;

    private final String[] LINCENSE_LEVELS_ALLOWED = {"A", "B", "C", "AB", "AC","ABC"};

    public List<DriverResponseDto> findAll() {
        return driverRepository.findAll()
                .stream().map(d -> convertToDto(d, DriverResponseDto.class))
                .toList();
    }

    public void save(DriverRequestDto driverRequestDto) {
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

    @Transactional
    public void update(String id, DriverRequestDto driverRequestDto) {

        Optional<Driver> driverOptional = driverRepository.findById(stringToUuid(id));

        if (driverOptional.isEmpty()) {
            throw new RecordNotFoundException("Motorsita não encontrado");
        }

        Driver driver = convertToEntity(driverRequestDto, Driver.class);

        if (!checkIfLicenceLevelIsValid(driver.getDriverLicenceLevel())) {
            throw new IllegalArgumentException("Tipo de carteira de motorista invalido. Tipos válidos: "
                    + ArrayUtils.toString(LINCENSE_LEVELS_ALLOWED).replaceAll("[{}]", ""));
        }

        driverRepository.save(driver);
    }

    public void delete(String id) {
        Optional<Driver> driverOptional = driverRepository.findById(stringToUuid(id));

        if (driverOptional.isEmpty()) {
            throw new RecordNotFoundException("Motorista não encontrado");
        }

        Driver driver = driverOptional.get();
        driverRepository.delete(driver);

    }

    @Override
    public <E, D> D convertToDto(E entity, Class<D> dClass) {
        Driver driver = (Driver) entity;
        DriverResponseDto dto
                = new DriverResponseDto(driver.getDriverLicenceLevel(), driver.getDriver().getNome(), driver.getDriver().getEmail());

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
