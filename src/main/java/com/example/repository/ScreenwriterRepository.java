package com.example.repository;

import com.example.entity.pivots.worker.Screenwriter;
import com.example.entity.pivots.worker.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScreenwriterRepository extends JpaRepository<Screenwriter, Long> {
    Screenwriter findByWorker(Worker worker);
}
