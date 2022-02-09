package com.example.repository;

import com.example.entity.pivots.plot.Genres;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface GenreRepository extends JpaRepository<Genres, Long> {
    Optional<Genres> findByGenre(String name);

    @Modifying
    @Transactional
    @Query(value = "delete from genres where id = :id", nativeQuery = true)
    void deleteById(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query(value = "delete from genres where genre = :name", nativeQuery = true)
    void deleteByName(@Param("name") String name);

    @Modifying
    @Transactional
    @Query(value = "delete from plot_genres where genre_id = :id", nativeQuery = true)
    void deleteFromPlots(@Param("id") Long genreId);

    @Modifying
    @Transactional
    @Query(value = "update genres set genre = :genre, description = :description where id = :id", nativeQuery = true)
    void setInfoById(@Param("id") Long genreId, @Param("genre") String genre, @Param("description") String description);

    @Modifying
    @Transactional
    @Query(value = "update genres set description = :description where genre = :genre", nativeQuery = true)
    void setInfoByName(@Param("genre") String name, @Param("description") String description);

    @Transactional
    @Query(value = "select * from genres join plot_genres pg on genres.id = pg.genre_id where plot_id = :id", nativeQuery = true)
    List<Genres> findPlotGenres(@Param("id") Long plotId);
}
