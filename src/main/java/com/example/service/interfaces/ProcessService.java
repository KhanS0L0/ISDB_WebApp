package com.example.service.interfaces;

import com.example.dto.PivotDTO.ProcessDTO;
import com.example.entity.pivots.processes.Process;

import java.util.List;

public interface ProcessService {
    Process addProcess(ProcessDTO processDTO, Long workerId);

    List<Process> getAllProcesses(Long workerId);

    Process findByDescription(String description);

    Process findById(Long processId);

    void delete(Long workerId, Long processId);
}
