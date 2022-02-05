package com.example.service.interfaces;

import com.example.dto.PivotDTO.AbilityDTO;
import com.example.entity.enums.AbilityType;
import com.example.entity.pivots.characters.Ability;
import com.example.exceptions.AbilityAlreadyExistException;

import java.util.List;

public interface AbilityService {

    AbilityDTO addAbility(AbilityDTO abilityDTO) throws AbilityAlreadyExistException;

    List<AbilityDTO> getAll();

    AbilityDTO findById(Long abilityId);

    List<AbilityDTO> findAllByType(String abilityType);

    boolean delete(Long abilityId);

}
