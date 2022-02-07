package com.example.controller;

import com.example.dto.PivotDTO.AbilityDTO;
import com.example.exceptions.AbilityAlreadyExistException;
import com.example.exceptions.AbilityNotFoundException;
import com.example.service.interfaces.AbilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/abilities")
public class AbilityController {

    private final AbilityService abilityService;

    @Autowired
    public AbilityController(AbilityService abilityService) {
        this.abilityService = abilityService;
    }

    @GetMapping(path = "/all", produces = "application/json")
    public ResponseEntity getAllAbilities(){
        List<AbilityDTO> response = abilityService.getAll();
        if(response.size() == 0)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Abilities do not exist");
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/id/{id}", produces = "application/json")
    public ResponseEntity getAbility(@PathVariable("id") Long abilityId){
        try{
            AbilityDTO abilityDTO = abilityService.findById(abilityId);
            return ResponseEntity.ok(abilityDTO);
        }catch (AbilityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping(path = "/type/{type}", produces = "application/json")
    public ResponseEntity getAbilityByType(@PathVariable("type") String type){
        List<AbilityDTO> abilities = abilityService.findAllByType(type);
        if(abilities.size() == 0)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Abilities with type: " + type + " do not exist");
        return ResponseEntity.ok(abilities);
    }

    @PostMapping(produces = "application/json")
    public ResponseEntity createAbility(@RequestBody AbilityDTO abilityDTO){
        try {
            abilityDTO = abilityService.addAbility(abilityDTO);
            if(abilityDTO != null && abilityDTO.getId() != null)
                return ResponseEntity.status(HttpStatus.CREATED).body(abilityDTO);
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body("Failed to create ability");
        } catch (AbilityAlreadyExistException e) {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(e.getMessage());
        }
    }

    @PatchMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity updateAbility(@PathVariable("id") Long abilityId, @RequestBody AbilityDTO abilityDTO){
        try {
            abilityService.update(abilityId, abilityDTO);
            return ResponseEntity.ok("Ability with id: " + abilityId + " successfully updated");
        } catch (AbilityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity deleteAbility(@PathVariable("id") Long abilityId){
        try {
            abilityService.delete(abilityId);
            return ResponseEntity.ok("Ability with id: " + abilityId + " successfully deleted");
        } catch (AbilityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(e.getMessage());
        }
    }
}
