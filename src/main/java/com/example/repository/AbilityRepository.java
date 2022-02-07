package com.example.repository;

import com.example.entity.enums.AbilityType;
import com.example.entity.pivots.characters.Ability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface AbilityRepository extends JpaRepository<Ability, Long> {
    List<Ability> findAllByAbilityType(AbilityType abilityType);

    Optional<Ability> findByDescription(String description);

    @Modifying
    @Transactional
    @Query(value = "delete from ability where id = :id", nativeQuery = true)
    void deleteById(@Param("id") Long abilityId);

    @Modifying
    @Transactional
    @Query(value = "delete from abilities_characters where ability_id = :id", nativeQuery = true)
    void deleteFromCharacterTable(@Param("id") Long abilityId);

    @Modifying
    @Transactional
    @Query(value = "update ability set ability_type = :type, description = :description where id = :id", nativeQuery = true)
    void setAbilityInfoById(@Param("id") Long id, @Param("type") String type, @Param("description") String description);
}
