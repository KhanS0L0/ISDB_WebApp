package com.example.service.interfaces;

import com.example.dto.PivotDTO.ProcessDTO;
import com.example.entity.pivots.processes.Process;
import com.example.exceptions.ProcessNotFoundException;

import java.util.List;

public interface ProcessService {
    ProcessDTO addProcess(ProcessDTO processDTO, Long workerId);

    List<ProcessDTO> getAllProcesses(Long workerId);

    ProcessDTO findByDescription(String description);

    ProcessDTO findById(Long processId) throws ProcessNotFoundException;

    boolean delete(Long workerId, Long processId);
}
