package com.example.controller;

import com.example.dto.JoinDTO;
import com.example.dto.ProcessDTO.ProcessDTO;
import com.example.exceptions.notFoundExceptions.ArtistNotFoundException;
import com.example.exceptions.notFoundExceptions.ProcessNotFoundException;
import com.example.exceptions.notFoundExceptions.ScreenwriterNotFoundException;
import com.example.service.interfaces.ProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/process")
public class ProcessController {

    private final ProcessService processService;

    @Autowired
    public ProcessController(ProcessService processService) {
        this.processService = processService;
    }

    @GetMapping(path = "/all", produces = "application/json")
    public ResponseEntity getAllProcess(@RequestAttribute("workerId") Long workerId){
        List<ProcessDTO> response = processService.getAllProcesses(workerId);
        if(response.size() == 0){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User dont have any processes");
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity getProcess(@PathVariable("id") Long processId){
        try{
            ProcessDTO response = processService.findById(processId);
            return ResponseEntity.ok(response);
        }catch(ProcessNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping(produces = "application/json")
    public ResponseEntity createProcess(@RequestAttribute("workerId") Long workerId, @RequestBody ProcessDTO processDTO){
        processDTO = processService.addProcess(processDTO, workerId);
        if(processDTO != null && processDTO.getId() != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(processDTO);
        }
        return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body("Failed to create process");
    }

    @PostMapping(path = "/joinArtist", produces = "application/json")
    public ResponseEntity joinArtist(@RequestBody JoinDTO joinDTO){
        try {
            processService.addArtist(joinDTO.getFirstId(), joinDTO.getSecondId());
            return ResponseEntity.ok("Worker with id: " + joinDTO.getFirstId() + " successfully added to process");
        } catch (ArtistNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping(path = "/joinScreenwriter", produces = "application/json")
    public ResponseEntity joinScreenwriter(@RequestBody JoinDTO joinDTO){
        try{
            processService.addScreenwriter(joinDTO.getFirstId(), joinDTO.getSecondId());
            return ResponseEntity.ok("Worker with id: " + joinDTO.getFirstId() + " successfully added to process");
        }catch (ScreenwriterNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PatchMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity updateProcess(@PathVariable("id") Long processId,
                                        @RequestAttribute("workerId") Long workerId,
                                        @RequestBody ProcessDTO processDTO){
        try {
            processService.update(workerId, processId, processDTO);
            return ResponseEntity.ok("Process with id " + processId + " successfully updated");
        } catch (ProcessNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity deleteProcess(@RequestAttribute("workerId") Long workerId, @PathVariable("id") Long processId){
        if(processService.delete(workerId, processId)){
            return ResponseEntity.ok("Process with id: " + processId + " successfully deleted");
        }
        return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body("Process with id: " + processId + "failed to delete");
    }
}
