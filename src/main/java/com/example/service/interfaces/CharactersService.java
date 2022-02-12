package com.example.service.interfaces;

import com.example.dto.PivotDTO.AbilityDTO;
import com.example.dto.PivotDTO.CharacterDTO;
import com.example.dto.PivotDTO.PlotDTO;
import com.example.exceptions.notFoundExceptions.AbilityNotFoundException;
import com.example.exceptions.alreadyExistExceptions.CharacterAlreadyExistException;
import com.example.exceptions.notFoundExceptions.CharacterNotFoundException;
import com.example.exceptions.notFoundExceptions.PlotNotFoundException;

import java.util.List;

public interface CharactersService {
    CharacterDTO addCharacter(CharacterDTO characterDTO) throws CharacterAlreadyExistException;

    CharacterDTO findById(Long characterId) throws CharacterNotFoundException;

    List<PlotDTO> findCharacterPlots(Long characterId) throws PlotNotFoundException;

    List<CharacterDTO> findAll();

    List<CharacterDTO> findAllByType(String type);

    List<AbilityDTO> findCharacterAbilities(Long characterId);

    void addAbility(Long characterId, Long abilityId) throws AbilityNotFoundException, CharacterNotFoundException;

    void deleteAbility(Long characterId, Long abilityId) throws CharacterNotFoundException;

    void update(Long characterId, CharacterDTO characterDTO) throws CharacterNotFoundException;

    boolean delete(Long characterId) throws CharacterNotFoundException;
}
