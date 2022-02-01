package com.example.controller;

import com.example.dto.PivotDTO.ProcessDTO;
import com.example.exceptions.ProcessNotFoundException;
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
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Process with id: " + processId + " not found");
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

    @DeleteMapping(path = "/{id}")
    public ResponseEntity deleteProcess(@RequestAttribute("workerId") Long workerId, @PathVariable("id") Long processId){
        if(processService.delete(workerId, processId)){
            return ResponseEntity.ok("Process with id: " + processId + " successfully deleted");
        }
        return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body("Process with id: " + processId + "failed to delete");
    }
}
