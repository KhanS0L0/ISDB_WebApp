package com.example.repository;

import com.example.entity.pivots.worker.Artist;
import com.example.entity.pivots.worker.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {
    Artist findByWorker(Worker worker);

    Artist findByWorkerId(Long workerId);
}
