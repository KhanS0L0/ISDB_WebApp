package com.example.service.implementations;

import com.example.dto.PivotDTO.ProcessDTO;
import com.example.entity.pivots.processes.Process;
import com.example.entity.pivots.worker.Artist;
import com.example.entity.pivots.worker.Screenwriter;
import com.example.entity.pivots.worker.Worker;
import com.example.exceptions.ProcessNotFoundException;
import com.example.repository.ArtistRepository;
import com.example.repository.ProcessRepository;
import com.example.repository.ScreenwriterRepository;
import com.example.repository.WorkerRepository;
import com.example.service.interfaces.ProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ProcessServiceImpl implements ProcessService {

    private final ProcessRepository processRepository;
    private final WorkerRepository workerRepository;
    private final ArtistRepository artistRepository;
    private final ScreenwriterRepository screenwriterRepository;

    @Autowired
    public ProcessServiceImpl(ProcessRepository processRepository,
                              WorkerRepository workerRepository,
                              ArtistRepository artistRepository,
                              ScreenwriterRepository screenwriterRepository) {
        this.processRepository = processRepository;
        this.workerRepository = workerRepository;
        this.artistRepository = artistRepository;
        this.screenwriterRepository = screenwriterRepository;
    }

    @Override
    public ProcessDTO addProcess(ProcessDTO newProcess, Long workerId) {
        String type = Process.isArtistOrScreenwriterProcess(newProcess);
        Worker worker = workerRepository.findById(workerId).orElse(null);

        if(worker != null && type != null){

            Process process = Process.create(newProcess);

            if(type.equals("Artist")){
                Artist artist = artistRepository.findByWorker(worker);
                process.setArtists(Collections.singletonList(artist));
            }else if(type.equals("Screenwriter")){
                Screenwriter screenwriter = screenwriterRepository.findByWorker(worker);
                process.setScreenwriters(Collections.singletonList(screenwriter));
            }

            // todo: первичный ключ почему-то дублируется
            return new ProcessDTO().toProcessDTO(processRepository.save(process));
        }
        return null;
    }

    @Override
    public List<ProcessDTO> getAllProcesses(Long workerId){
        List<Process> processes = processRepository.findArtistProcessByWorkerId(workerId);
        processes.addAll(processRepository.findScreenwriterProcessByWorkerId(workerId));

        List<ProcessDTO> result = new ArrayList<>();
        processes.forEach(process -> result.add(new ProcessDTO().toProcessDTO(process)));

        return result;
    }

    @Override
    public ProcessDTO findByDescription(String description){
        ProcessDTO result = new ProcessDTO().toProcessDTO(processRepository.findByDescription(description));
        return result;
    }

    @Override
    public ProcessDTO findById(Long processId) throws ProcessNotFoundException {
        Process process =  processRepository.findById(processId).orElse(null);
        if(process == null)
            throw new ProcessNotFoundException("Process with id: " + processId + " not found");
        return new ProcessDTO().toProcessDTO(process);
    }

    @Override
    public boolean delete(Long workerId, Long processId) {
        List<ProcessDTO> processes = this.getAllProcesses(workerId);
        int size_before = processes.size();
        processes.stream().filter(p -> p.getId().equals(processId))
                .findAny().ifPresent(processDTO -> processRepository.deleteById(processDTO.getId()));
        int size_after = this.getAllProcesses(workerId).size();
        if(size_before == size_after){
            return false;
        }
        return true;
    }
}
