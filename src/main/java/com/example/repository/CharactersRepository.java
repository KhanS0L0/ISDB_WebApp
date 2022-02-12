package com.example.repository;

import com.example.entity.enums.CharactersType;
import com.example.entity.pivots.characters.Ability;
import com.example.entity.pivots.characters.Characters;
import com.example.entity.pivots.plot.Plot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface CharactersRepository extends JpaRepository<Characters, Long> {

    List<Characters> findAllByCharactersType(CharactersType charactersType);

    Optional<Characters> findByNameAndDescription(String name, String description);

    @Transactional
    @Query(value = "select * from plot join plots_characters pc on plot.id = pc.plot_id where character_id = :id", nativeQuery = true)
    List<Plot> findCharacterPlots(@Param("id") Long characterId);

    @Modifying
    @Transactional
    @Query(value = "delete from characters where id = :id", nativeQuery = true)
    void delete(@Param("id") Long characterId);

    @Modifying
    @Transactional
    @Query(value = "delete from abilities_characters where character_id = :characterId and ability_id = :abilityId", nativeQuery = true)
    void deleteAbility(@Param("characterId") Long characterId, @Param("abilityId") Long abilityId);

    @Modifying
    @Transactional
    @Query(value = "delete from abilities_characters where character_id = :id", nativeQuery = true)
    void deleteAbilities(@Param("id") Long characterId);

    @Modifying
    @Transactional
    @Query(value = "update characters set name = :name, age = :age, description = :description, character_type = :type where id = :id", nativeQuery = true)
    void setCharacterInfoById(
            @Param("id") Long characterId,
            @Param("name") String name,
            @Param("age") int age,
            @Param("description") String description,
            @Param("type") String type);
}
