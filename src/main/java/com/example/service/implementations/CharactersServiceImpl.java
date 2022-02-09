package com.example.service.implementations;

import com.example.dto.PivotDTO.AbilityDTO;
import com.example.dto.PivotDTO.CharacterDTO;
import com.example.entity.enums.CharactersType;
import com.example.entity.pivots.characters.Ability;
import com.example.entity.pivots.characters.Characters;
import com.example.exceptions.AbilityNotFoundException;
import com.example.exceptions.CharacterAlreadyExistException;
import com.example.exceptions.CharacterNotFoundException;
import com.example.repository.AbilityRepository;
import com.example.repository.CharactersRepository;
import com.example.service.interfaces.CharactersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CharactersServiceImpl implements CharactersService {

    private final CharactersRepository charactersRepository;
    private final AbilityRepository abilityRepository;

    @Autowired
    public CharactersServiceImpl(CharactersRepository charactersRepository, AbilityRepository abilityRepository) {
        this.charactersRepository = charactersRepository;
        this.abilityRepository = abilityRepository;
    }

    @Override
    public CharacterDTO findById(Long characterId) throws CharacterNotFoundException {
        Characters character = charactersRepository.findById(characterId).orElse(null);
        if(character == null)
            throw new CharacterNotFoundException("Character with id: " + characterId + " not found");
        return CharacterDTO.fromCharacter(character);
    }

    @Override
    public CharacterDTO addCharacter(CharacterDTO characterDTO) throws CharacterAlreadyExistException {
        Characters character = charactersRepository.findByNameAndDescription(characterDTO.getName(), characterDTO.getDescription()).orElse(null);
        if(character != null)
            throw new CharacterAlreadyExistException("Character with name: " + character.getName() + " and \n description: " + character.getDescription() + " already exist");
        character = charactersRepository.save(characterDTO.toCharacter());
        return CharacterDTO.fromCharacter(character);
    }

    @Override
    public List<CharacterDTO> findAll() {
        List<Characters> characters = charactersRepository.findAll();
        List<CharacterDTO> result = new ArrayList<>();
        characters.forEach(character -> result.add(CharacterDTO.fromCharacter(character)));
        return result;
    }

    @Override
    public List<CharacterDTO> findAllByType(String type) {
        List<Characters> characters = charactersRepository.findAllByCharactersType(CharactersType.valueOf(type));
        List<CharacterDTO> result = new ArrayList<>();
        characters.forEach(character -> result.add(CharacterDTO.fromCharacter(character)));
        return result;
    }

    @Override
    public List<AbilityDTO> findCharacterAbilities(Long characterId) {
        List<Ability> abilities = charactersRepository.findById(characterId).get().getAbilities();
        List<AbilityDTO> abilityDTOS = new ArrayList<>();
        abilities.forEach(ability -> abilityDTOS.add(AbilityDTO.fromAbility(ability)));
        return abilityDTOS;
    }


    @Override
    public void addAbility(Long characterId, Long abilityId) throws AbilityNotFoundException, CharacterNotFoundException {
        Ability ability = abilityRepository.findById(abilityId).orElse(null);
        if (ability == null)
            throw new AbilityNotFoundException("Ability with id: " + abilityId + " not found");
        Characters character = charactersRepository.findById(characterId).orElse(null);
        if (character == null)
            throw new CharacterNotFoundException("Character with id: " + characterId + " not found");
        List<Ability> abilities = character.getAbilities();
        if (!abilities.contains(ability)){
            abilities.add(ability);
            charactersRepository.save(character);
        }
    }

    @Override
    public void deleteAbility(Long characterId, Long abilityId) throws CharacterNotFoundException {
        Characters character = charactersRepository.findById(characterId).orElse(null);
        if(character == null)
            throw new CharacterNotFoundException("Character with id: " + characterId + " not found");
        List<Ability> abilities = character.getAbilities();
        if(!abilities.isEmpty() && abilities.stream().anyMatch(ability -> ability.getId().equals(abilityId)))
            charactersRepository.deleteAbility(characterId, abilityId);
    }

    @Override
    public void update(Long characterId, CharacterDTO updatedCharacter) throws CharacterNotFoundException {
        CharacterDTO characterDTO = this.findById(characterId);
        characterDTO.checkFields(updatedCharacter);
        charactersRepository.setCharacterInfoById(
                characterDTO.getId(),
                characterDTO.getName(),
                characterDTO.getAge(),
                characterDTO.getDescription(),
                characterDTO.getType()
        );
    }

    @Override
    public boolean delete(Long characterId) throws CharacterNotFoundException {
        CharacterDTO character = this.findById(characterId);
        if(character == null || character.getId() == null)
            throw new CharacterNotFoundException("Character with id: " + characterId + " not found");
        charactersRepository.delete(characterId);
        charactersRepository.deleteAbilities(characterId);
        return false;
    }
}
