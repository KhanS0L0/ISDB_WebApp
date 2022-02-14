package com.example.controller;

import com.example.dto.JoinDTO;
import com.example.dto.PivotDTO.TextDTO;
import com.example.entity.enums.ShellType;
import com.example.exceptions.alreadyExistExceptions.TextAlreadyExistException;
import com.example.exceptions.notFoundExceptions.EffectNotFoundException;
import com.example.exceptions.notFoundExceptions.PageNotFoundException;
import com.example.exceptions.notFoundExceptions.TextNotFoundException;
import com.example.service.interfaces.TextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/texts")
public class TextController {

    private final TextService textService;

    @Autowired
    public TextController(TextService textService) {
        this.textService = textService;
    }

    @GetMapping(path = "/all", produces = "application/json")
    public ResponseEntity getAll(){
        try {
            List<TextDTO> textDTOList = textService.getAll();
            return ResponseEntity.ok(textDTOList);
        } catch (TextNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(path = "/type/{type}", produces = "application/json")
    public ResponseEntity getAllByType(@PathVariable("type") String type){
        try {
            List<TextDTO> textDTOS = textService.findAllByType(ShellType.valueOf(type));
            return ResponseEntity.ok(textDTOS);
        } catch (TextNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(path = "/id/{id}", produces = "application/json")
    public ResponseEntity getById(@PathVariable("id") Long textId){
        try {
            TextDTO textDTO = textService.findById(textId);
            return ResponseEntity.ok(textDTO);
        } catch (TextNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(path = "/addText", produces = "application/json")
    public ResponseEntity addText(@RequestBody TextDTO textDTO){
        try {
            TextDTO response = textService.addText(textDTO);
            return ResponseEntity.ok(response);
        } catch (TextAlreadyExistException | PageNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(path = "/addEffect", produces = "application/json")
    public ResponseEntity addEffect(@RequestBody JoinDTO joinDTO){
        try {
            textService.addEffect(joinDTO);
            return ResponseEntity.ok("Effect successfully added to text");
        } catch (TextNotFoundException | EffectNotFoundException e) {
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping(path = "/update/{id}", produces = "application/json")
    public ResponseEntity update(@PathVariable("id") Long textId, @RequestBody TextDTO textDTO){
        try {
            textService.update(textId, textDTO);
            return ResponseEntity.ok("Text successfully updated");
        } catch (TextNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping(path = "/delete/{id}", produces = "application/json")
    public ResponseEntity delete(@PathVariable("id") Long textId){
        try {
            textService.delete(textId);
            return ResponseEntity.ok("Text successfully deleted");
        } catch (TextNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
