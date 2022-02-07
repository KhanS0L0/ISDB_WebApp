package com.example.repository;

import com.example.entity.pivots.processes.Process;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
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
    Process findByDescription(@Param("description") String description);

    @Modifying
    @Transactional
    @Query( value = "update process set process_type = :processType, process_status = :processStatus, description = :description, begin_date = :beginDate, deadline = :deadline where id = :id",
            nativeQuery = true
    )
    void setProcessInfoById(
            @Param("id") Long id,
            @Param("processType") String processType,
            @Param("processStatus") String processStatus,
            @Param("description") String description,
            @Param("beginDate") Timestamp beginDate,
            @Param("deadline") Timestamp deadline);

    @Modifying
    @Transactional
    @Query(value = "delete from artists_processes where process_id = :id", nativeQuery = true)
    void deleteFromArtist(@Param("id") Long processId);

    @Modifying
    @Transactional
    @Query(value = "delete from screenwriters_processes where process_id = :id", nativeQuery = true)
    void deleteFromScreenwriters(@Param("id") Long processId);

    @Modifying
    @Transactional
    @Query(value = "delete from process where id = :id", nativeQuery = true)
    void deleteById(@Param("id") Long processId);

    @Modifying
    @Transactional
    @Query(value = "insert into screenwriters_processes(screenwriter_id, process_id) values (:screenwriterId, :processId)", nativeQuery = true)
    void joinScreenwriter(@Param("screenwriterId") Long screenwriterId, @Param("processId") Long processId);

    @Modifying
    @Transactional
    @Query(value = "insert into artists_processes(artist_id, process_id) values (:artistId, :processId)", nativeQuery = true)
    void joinArtist(@Param("artistId") Long artistId, @Param("processId") Long processId);
}