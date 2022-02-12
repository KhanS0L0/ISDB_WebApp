package com.example.controller;

import com.example.dto.JoinDTO;
import com.example.dto.PivotDTO.AbilityDTO;
import com.example.dto.PivotDTO.CharacterDTO;
import com.example.dto.PivotDTO.PlotDTO;
import com.example.exceptions.notFoundExceptions.AbilityNotFoundException;
import com.example.exceptions.alreadyExistExceptions.CharacterAlreadyExistException;
import com.example.exceptions.notFoundExceptions.CharacterNotFoundException;
import com.example.exceptions.notFoundExceptions.PlotNotFoundException;
import com.example.service.interfaces.CharactersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/characters")
public class CharacterController {

    private final CharactersService charactersService;

    @Autowired
    public CharacterController(CharactersService charactersService) {
        this.charactersService = charactersService;
    }

    @GetMapping(path = "/all", produces = "application/json")
    public ResponseEntity getAll(){
        List<CharacterDTO> response = charactersService.findAll();
        if(response.size() == 0)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Characters do not exist");
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/id/{id}", produces = "application/json")
    public ResponseEntity getCharacter(@PathVariable("id") Long characterId){
        try {
            CharacterDTO characterDTO = charactersService.findById(characterId);
            return ResponseEntity.ok(characterDTO);
        } catch (CharacterNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping(path = "/id/{id}/plots", produces = "application/json")
    public ResponseEntity getAllPlots(@PathVariable("id") Long characterId){
        try {
            List<PlotDTO> response = charactersService.findCharacterPlots(characterId);
            return ResponseEntity.ok(response);
        } catch (PlotNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(path = "/type/{type}", produces = "application/json")
    public ResponseEntity getCharacterByType(@PathVariable("type") String type){
        List<CharacterDTO> characters = charactersService.findAllByType(type);
        if(characters.size() == 0)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Characters with type: " + type + " do not exist");
        return ResponseEntity.ok(characters);
    }

    @GetMapping(path = "/{id}/abilities")
    public ResponseEntity getAbilities(@PathVariable("id") Long characterId){
        List<AbilityDTO> abilities = charactersService.findCharacterAbilities(characterId);
        if(abilities.size() == 0)
            return ResponseEntity.ok("Character with id: " + characterId + " does not have any abilities");
        return ResponseEntity.ok(abilities);
    }

    @PostMapping(produces = "application/json")
    public ResponseEntity addCharacter(@RequestBody CharacterDTO characterDTO){
        try {
            characterDTO = charactersService.addCharacter(characterDTO);
            if(characterDTO != null && characterDTO.getId() != null)
                return ResponseEntity.status(HttpStatus.CREATED).body(characterDTO);
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body("Failed to create character");
        } catch (CharacterAlreadyExistException e) {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(e.getMessage());
        }
    }

    @PostMapping(path = "/addAbility", produces = "application/json")
    public ResponseEntity addAbility(@RequestBody JoinDTO joinDTO){
        try {
            charactersService.addAbility(joinDTO.getFirstId(), joinDTO.getSecondId());
            return ResponseEntity.ok("Ability successfully added to character");
        } catch (AbilityNotFoundException | CharacterNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PatchMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity updateCharacter(@PathVariable("id") Long characterId, @RequestBody CharacterDTO characterDTO){
        try {
            charactersService.update(characterId, characterDTO);
            return ResponseEntity.ok("Character with id: " + characterId + " successfully updated");
        } catch (CharacterNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping(path = "/deleteAbility", produces = "application/json")
    public ResponseEntity deleteAbility(@RequestBody JoinDTO joinDTO){
        try {
            charactersService.deleteAbility(joinDTO.getFirstId(), joinDTO.getSecondId());
            return ResponseEntity.ok("Ability successfully deleted");
        } catch (CharacterNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity deleteCharacter(@PathVariable("id") Long characterId){
        try {
            charactersService.delete(characterId);
            return ResponseEntity.ok("Character with id: " + characterId + " successfully deleted");
        } catch (CharacterNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(e.getMessage());
        }
    }
}
