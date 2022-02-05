package com.example.service.implementations;

import com.example.dto.PivotDTO.AbilityDTO;
import com.example.entity.enums.AbilityType;
import com.example.entity.pivots.characters.Ability;
import com.example.exceptions.AbilityAlreadyExistException;
import com.example.repository.AbilityRepository;
import com.example.service.interfaces.AbilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AbilityServiceImpl implements AbilityService {

    private final AbilityRepository abilityRepository;

    @Autowired
    public AbilityServiceImpl(AbilityRepository abilityRepository) {
        this.abilityRepository = abilityRepository;
    }

    @Override
    public AbilityDTO addAbility(AbilityDTO abilityDTO) throws AbilityAlreadyExistException {
        Ability ability = abilityRepository.findById(abilityDTO.getId()).orElse(null);
        if(ability != null){
            throw new AbilityAlreadyExistException("Ability already exists");
        }
        ability = abilityRepository.save(abilityDTO.toAbility());
        return AbilityDTO.fromAbility(ability);
    }

    @Override
    public List<AbilityDTO> getAll() {
        List<Ability> abilityList = abilityRepository.findAll();
        List<AbilityDTO> result = new ArrayList<>();
        abilityList.forEach(ability -> result.add(AbilityDTO.fromAbility(ability)));
        return result;
    }

    @Override
    public AbilityDTO findById(Long abilityId) {
        Ability ability = abilityRepository.findById(abilityId).orElse(null);
        if(ability == null){
            throw new NullPointerException("Ability with id" + abilityId + " not found");
        }
        return AbilityDTO.fromAbility(ability);
    }

    @Override
    public List<AbilityDTO> findAllByType(String abilityType) {
        List<Ability> abilityList = abilityRepository.findAllByAbilityType(AbilityType.valueOf(abilityType));
        List<AbilityDTO> result = new ArrayList<>();
        abilityList.forEach(ability -> result.add(AbilityDTO.fromAbility(ability)));
        return result;
    }

    @Override
    public boolean delete(Long abilityId) {
        Ability ability = abilityRepository.findById(abilityId).orElse(null);
        if(ability == null){
            throw new NullPointerException("Ability with id" + abilityId + " not found");
        }
        abilityRepository.deleteById(abilityId);
        ability = abilityRepository.findById(abilityId).orElse(null);
        return ability == null;
    }
}
