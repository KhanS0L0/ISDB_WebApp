package com.example.service.interfaces;

import com.example.dto.PivotDTO.AbilityDTO;
import com.example.dto.PivotDTO.CharacterDTO;
import com.example.exceptions.AbilityNotFoundException;
import com.example.exceptions.CharacterAlreadyExistException;
import com.example.exceptions.CharacterNotFoundException;

import java.util.List;

public interface CharactersService {
    CharacterDTO addCharacter(CharacterDTO characterDTO) throws CharacterAlreadyExistException;

    CharacterDTO findById(Long characterId) throws CharacterNotFoundException;

    List<CharacterDTO> findAll();

    List<CharacterDTO> findAllByType(String type);

    List<AbilityDTO> findCharacterAbilities(Long characterId);

    void addAbility(Long characterId, Long abilityId) throws AbilityNotFoundException, CharacterNotFoundException;

    void deleteAbility(Long characterId, Long abilityId) throws CharacterNotFoundException;

    void update(Long characterId, CharacterDTO characterDTO) throws CharacterNotFoundException;

    boolean delete(Long characterId) throws CharacterNotFoundException;
}
