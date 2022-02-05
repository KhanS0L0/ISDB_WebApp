package com.example.repository;

import com.example.entity.enums.AbilityType;
import com.example.entity.pivots.characters.Ability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AbilityRepository extends JpaRepository<Ability, Long> {
    List<Ability> findAllByAbilityType(AbilityType abilityType);
}
