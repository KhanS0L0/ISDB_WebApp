package com.example.repository;

import com.example.entity.pivots.processes.Process;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProcessRepository extends JpaRepository<Process, Long> {
    @Query(
            value = "select * from process p " +
            "join artists_processes ap on p.id = ap.process_id " +
            "join artist a on a.id = ap.artist_id " +
            "where a.worker_id = :workerId",
            nativeQuery = true
    )
    List<Process> findArtistProcessByWorkerId(@Param("workerId") Long workerId);

    @Query(
            value = "select * from process p " +
            "join screenwriters_processes sp on p.id = sp.process_id " +
            "join screenwriter s on s.id = sp.screenwriter_id " +
            "where s.worker_id = :workerId",
            nativeQuery = true
    )
    List<Process> findScreenwriterProcessByWorkerId(@Param("workerId") Long workerId);

    @Query(
            value = "select * from process p where p.description = :description",
            nativeQuery = true
    )
    Process findByDescription(
            @Param("description") String description);

    void deleteById(Long id);
}
