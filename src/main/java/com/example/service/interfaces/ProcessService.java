package com.example.service.interfaces;

import com.example.dto.ProcessDTO.ProcessDTO;
import com.example.exceptions.ArtistNotFoundException;
import com.example.exceptions.ProcessNotFoundException;
import com.example.exceptions.ScreenwriterNotFoundException;

import java.util.List;

public interface ProcessService {
    ProcessDTO addProcess(ProcessDTO processDTO, Long workerId);

    List<ProcessDTO> getAllProcesses(Long workerId);

    ProcessDTO findByDescription(String description);

    ProcessDTO findById(Long processId) throws ProcessNotFoundException;

    ProcessDTO findById(Long workerId, Long processId) throws ProcessNotFoundException;

    void addArtist(Long workerId, Long processId) throws ArtistNotFoundException;

    void addScreenwriter(Long workerId, Long processId) throws ScreenwriterNotFoundException;

    void update(Long workerId, Long processId, ProcessDTO processDTO) throws ProcessNotFoundException;

    boolean delete(Long workerId, Long processId);
}
