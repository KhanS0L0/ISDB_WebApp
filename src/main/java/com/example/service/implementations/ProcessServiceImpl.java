package com.example.service.implementations;

import com.example.dto.ProcessDTO.ProcessDTO;
import com.example.entity.pivots.processes.Process;
import com.example.entity.pivots.worker.Artist;
import com.example.entity.pivots.worker.Screenwriter;
import com.example.entity.pivots.worker.Worker;
import com.example.exceptions.notFoundExceptions.ArtistNotFoundException;
import com.example.exceptions.notFoundExceptions.ProcessNotFoundException;
import com.example.exceptions.notFoundExceptions.ScreenwriterNotFoundException;
import com.example.repository.ArtistRepository;
import com.example.repository.ProcessRepository;
import com.example.repository.ScreenwriterRepository;
import com.example.repository.WorkerRepository;
import com.example.service.interfaces.ProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
                List<Artist> artists = new ArrayList<>();
                artists.add(artistRepository.findByWorker(worker));
                process.setArtists(artists);
            }else if(type.equals("Screenwriter")){
                List<Screenwriter> screenwriters = new ArrayList<>();
                screenwriters.add(screenwriterRepository.findByWorker(worker));
                process.setScreenwriters(screenwriters);
            }
            process = processRepository.save(process);
            return new ProcessDTO().toProcessDTO(process);
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
        return new ProcessDTO().toProcessDTO(processRepository.findByDescription(description));
    }

    @Override
    public ProcessDTO findById(Long processId) throws ProcessNotFoundException {
        Process process =  processRepository.findById(processId).orElse(null);
        if(process == null)
            throw new ProcessNotFoundException("Process with id: " + processId + " not found");
        return new ProcessDTO().toProcessDTO(process);
    }

    @Override
    public ProcessDTO findById(Long workerId, Long processId) throws ProcessNotFoundException {
        List<ProcessDTO> processes = this.getAllProcesses(workerId);
        ProcessDTO result = processes.stream().filter(processDTO -> processDTO.getId().equals(processId)).findFirst().orElse(null);
        if(result == null)
            throw new ProcessNotFoundException("Process with id: " + processId + " not found");
        return result;
    }

    @Override
    public void addArtist(Long workerId, Long processId) throws ArtistNotFoundException {
        Artist artist = artistRepository.findByWorkerId(workerId);
        if(artist == null)
            throw new ArtistNotFoundException("Worker with id: " + workerId + " is not an artist");
        processRepository.joinArtist(artist.getId(), processId);
    }

    @Override
    public void addScreenwriter(Long workerId, Long processId) throws ScreenwriterNotFoundException {
        Screenwriter screenwriter = screenwriterRepository.findByWorkerId(workerId);
        if(screenwriter == null)
            throw new ScreenwriterNotFoundException("Worker with id: " + workerId + " is not a screenwriter");
        processRepository.joinScreenwriter(screenwriter.getId(), processId);
    }

    @Override
    public void update(Long workerId, Long processId, ProcessDTO processDTO) throws ProcessNotFoundException {
        ProcessDTO process = this.findById(workerId, processId);
        process.checkFields(processDTO);
        processRepository.setProcessInfoById(
                process.getId(),
                process.getProcessType(),
                process.getProcessStatus(),
                process.getDescription(),
                process.getBeginDate(),
                process.getDeadline()
        );
    }

    @Override
    public boolean delete(Long workerId, Long processId) {
        List<ProcessDTO> processes = this.getAllProcesses(workerId);
        ProcessDTO deletedProcess = processes.stream().filter(process -> process.getId().equals(processId)).findFirst().orElse(null);
        if (deletedProcess != null) {
            String type = Process.isArtistOrScreenwriterProcess(deletedProcess);
            if(type.equals("Artist")){
                processRepository.deleteFromArtist(processId);
                processRepository.deleteById(processId);
            }else if(type.equals("Screenwriter")){
                processRepository.deleteFromScreenwriters(processId);
                processRepository.deleteById(processId);
            }
            return !processRepository.findById(processId).isPresent();
        }
        return false;
    }
}
