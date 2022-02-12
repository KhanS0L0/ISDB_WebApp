package com.example.repository;

import com.example.entity.pivots.plot.Pages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface PageRepository extends JpaRepository<Pages, Long> {

    @Modifying
    @Transactional
    @Query(value = "insert into pages_effects (page_id, effect_id) values (:pageId, :effectId)", nativeQuery = true)
    void addEffect(@Param("pageId") Long pageId, @Param("effectId") Long effectId);

    @Transactional
    @Query(value = "select * from pages p where p.frame_count = :frame and p.plot_id = :plotId limit 1", nativeQuery = true)
    Optional<Pages> findByFrameCount(@Param("frame") int frameCount, @Param("plotId") Long plotId);

    @Transactional
    @Query(value = "select * from pages where plot_id = :id", nativeQuery = true)
    List<Pages> findAllByPlot(@Param("id") Long plotId);

    @Modifying
    @Transactional
    @Query(value = "update pages set frame_count = :frame, description = :description, plot_id = :plotId where id = :id", nativeQuery = true)
    void updateById(@Param("id") Long pageId, @Param("plotId") Long plotId, @Param("frame") int frame, @Param("description") String description);

    @Modifying
    @Transactional
    @Query(value = "delete from pages where id = :id", nativeQuery = true)
    void deleteById(@Param("id") Long pageId);

    @Modifying
    @Transactional
    @Query(value = "delete from pages_effects where page_id = :id", nativeQuery = true)
    void deleteFromEffects(@Param("id") Long pageId);
}
