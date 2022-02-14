package com.example.repository;

import com.example.entity.pivots.plot.Text;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface TextRepository extends JpaRepository<Text, Long> {

    Optional<Text> findById(Long textId);

    @Query(value = "select * from text where page_id = :pageId and description = :description limit 1", nativeQuery = true)
    Optional<Text> findByPageAndAndDescription(@Param("pageId") Long pageId, @Param("description") String description);

    @Modifying
    @Transactional
    @Query(value = "insert into texts_effects (text_id, effect_id) values (:textId, :effectId)", nativeQuery = true)
    void addEffect(@Param("textId") Long textId, @Param("effectId") Long effectId);

    @Transactional
    @Query(value = "select * from text where shell_type = :type", nativeQuery = true)
    List<Text> findAllByType(@Param("type") String type);

    @Modifying
    @Transactional
    @Query(value = "update text set page_id = :pageId, description = :description, shell_type = :shellType, font = :font, size = :size where id = :id", nativeQuery = true)
    void update(@Param("id") Long textId,
                @Param("pageId") Long pageId,
                @Param("description") String description,
                @Param("shellType") String type,
                @Param("font") String font,
                @Param("size") int size);

    @Modifying
    @Transactional
    @Query(value = "delete from text where id = :id", nativeQuery = true)
    void delete(@Param("id") Long textId);

    @Modifying
    @Transactional
    @Query(value = "delete from texts_effects where text_id = :id", nativeQuery = true)
    void deleteEffects(@Param("id") Long textId);
}
