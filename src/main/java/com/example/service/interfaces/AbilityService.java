package com.example.service.interfaces;

import com.example.dto.PivotDTO.AbilityDTO;
import com.example.exceptions.AbilityAlreadyExistException;
import com.example.exceptions.AbilityNotFoundException;

import java.util.List;

public interface AbilityService {

    AbilityDTO addAbility(AbilityDTO abilityDTO) throws AbilityAlreadyExistException;

    List<AbilityDTO> getAll();

    AbilityDTO findById(Long abilityId) throws AbilityNotFoundException;

    List<AbilityDTO> findAllByType(String abilityType);

    void update(Long abilityId, AbilityDTO updatedAbility) throws AbilityNotFoundException;

    void delete(Long abilityId) throws AbilityNotFoundException;

}
