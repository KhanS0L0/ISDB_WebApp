package com.example.repository;

import com.example.entity.pivots.plot.Effect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface EffectRepository extends JpaRepository<Effect, Long> {

    @Transactional
    @Query(value = "select * from effect where type = :type", nativeQuery = true)
    List<Effect> findAllByEffectType(@Param("type") String type);

    @Transactional
    @Query(value = "select * from effect join pages_effects pe on effect.id = pe.effect_id where pe.page_id = :id", nativeQuery = true)
    List<Effect> findByPage(@Param("id") Long pageId);

    @Modifying
    @Transactional
    @Query(value = "update effect set type = :type, description = :description where id = :id", nativeQuery = true)
    void updateById(@Param("id") Long effectId, @Param("type") String type, @Param("description") String description);

    @Modifying
    @Transactional
    @Query(value = "delete from effect where id = :id", nativeQuery = true)
    void deleteEffect(@Param("id") Long effectId);

    @Modifying
    @Transactional
    @Query(value = "delete from pages_effects where effect_id = :id", nativeQuery = true)
    void deleteFromPages(@Param("id") Long effectId);
}
