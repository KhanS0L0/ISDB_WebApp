package com.example.service.implementations;

import com.example.dto.PivotDTO.ProcessDTO;
import com.example.entity.pivots.processes.Process;
import com.example.entity.pivots.worker.Artist;
import com.example.entity.pivots.worker.Screenwriter;
import com.example.entity.pivots.worker.Worker;
import com.example.repository.ArtistRepository;
import com.example.repository.ProcessRepository;
import com.example.repository.ScreenwriterRepository;
import com.example.repository.WorkerRepository;
import com.example.service.interfaces.ProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Process addProcess(ProcessDTO newProcess, Long workerId) {
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

            return processRepository.save(process);
        }
        return null;
    }

    @Override
    public List<Process> getAllProcesses(Long workerId){
        List<Process> processes = processRepository.findArtistProcessByWorkerId(workerId);
        processes.addAll(processRepository.findScreenwriterProcessByWorkerId(workerId));
        return processes;
    }

    @Override
    public Process findByDescription(String description){
        return processRepository.findByDescription(description);
    }

    @Override
    public Process findById(Long processId) {
        return processRepository.findById(processId).orElse(null);
    }

    @Override
    public void delete(Long workerId, Long processId) {
        List<Process> processes = this.getAllProcesses(workerId);
        processes.stream().filter(p -> p.getId().equals(processId)).findAny().ifPresent(processRepository::delete);
    }
}
