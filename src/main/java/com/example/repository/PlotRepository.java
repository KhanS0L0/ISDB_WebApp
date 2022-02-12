package com.example.repository;

import com.example.entity.pivots.characters.Characters;
import com.example.entity.pivots.plot.Genres;
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
public interface PlotRepository extends JpaRepository<Plot, Long> {

    Optional<Plot> findById(Long plotId);

    Optional<Plot> findByDescription(String description);

    @Transactional
    @Query(value = "select * from characters join plots_characters pc on characters.id = pc.character_id where plot_id = :id", nativeQuery = true)
    List<Characters> findAllCharacter(@Param("id") Long plotId);

    @Modifying
    @Transactional
    @Query(value = "delete from plot where id = :id", nativeQuery = true)
    void delete(@Param("id") Long plotId);

    @Modifying
    @Transactional
    @Query(value = "delete from plot_genres where plot_id = :id", nativeQuery = true)
    void deleteFromGenre(@Param("id") Long plotId);

    @Modifying
    @Transactional
    @Query(value = "update plot set description = :description, number_of_arc = :arc where id = :id", nativeQuery = true)
    void setPlotInfoById(@Param("id") Long plotId, @Param("description") String description, @Param("arc") int arc);

    @Modifying
    @Transactional
    @Query(value = "insert into plot_genres (plot_id, genre_id) values (:plotId, :genreId)", nativeQuery = true)
    void addGenre(@Param("plotId") Long plotId, @Param("genreId") Long genreId);
}
