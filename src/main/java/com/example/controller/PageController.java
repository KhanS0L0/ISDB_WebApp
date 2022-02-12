package com.example.controller;


import com.example.dto.JoinDTO;
import com.example.dto.PivotDTO.EffectDTO;
import com.example.dto.PivotDTO.PageDTO;
import com.example.exceptions.alreadyExistExceptions.PageAlreadyExistException;
import com.example.exceptions.notFoundExceptions.EffectNotFoundException;
import com.example.exceptions.notFoundExceptions.PageNotFoundException;
import com.example.exceptions.notFoundExceptions.PlotNotFoundException;
import com.example.service.interfaces.EffectService;
import com.example.service.interfaces.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/pages")
public class PageController {

    private final PageService pageService;
    private final EffectService effectService;

    @Autowired
    public PageController(PageService pageService, EffectService effectService) {
        this.pageService = pageService;
        this.effectService = effectService;
    }

    @GetMapping(path = "/all", produces = "application/json")
    public ResponseEntity getAll(){
        try {
            List<PageDTO> response = pageService.getAll();
            return ResponseEntity.ok(response);
        } catch (PageNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(path = "/id/{id}", produces = "application/json")
    public ResponseEntity getById(@PathVariable("id") Long pageId){
        try {
            PageDTO page = pageService.findById(pageId);
            return ResponseEntity.ok(page);
        } catch (PageNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(path = "/frame/{plotId}/{frame}", produces = "application/json")
    public ResponseEntity getByFrame(@PathVariable("frame") int frame, @PathVariable("plotId") Long plotId){
        try {
            PageDTO page = pageService.findByFrameCount(frame, plotId);
            return ResponseEntity.ok(page);
        } catch (PageNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(path = "/plot/{id}/pages", produces = "application/json")
    public ResponseEntity getAllByPlot(@PathVariable("id") Long plotId){
        try {
            List<PageDTO> pages = pageService.findAllByPlot(plotId);
            return ResponseEntity.ok(pages);
        } catch (PageNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(path = "/effects", produces = "application/json")
    public ResponseEntity getAllEffects(){
        List<EffectDTO> response = effectService.getAll();
        if(response.size() == 0)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Effects do not exist");
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/effects/{id}", produces = "application/json")
    public ResponseEntity getEffectById(@PathVariable("id") Long effectId){
        try {
            EffectDTO effect = effectService.findById(effectId);
            return ResponseEntity.ok(effect);
        } catch (EffectNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(path = "/effects/type/{type}", produces = "application/json")
    public ResponseEntity getEffectsByType(@PathVariable("type") String type){
        try {
            List<EffectDTO> effects = effectService.findAllByType(type);
            return ResponseEntity.ok(effects);
        } catch (EffectNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(path = "/addPage", produces = "application/json")
    public ResponseEntity addPage(@RequestBody PageDTO pageDTO){
        try {
            PageDTO page = pageService.addPage(pageDTO);
            if(page != null && page.getId() != 0)
                return ResponseEntity.status(HttpStatus.CREATED).body(page);
            return ResponseEntity.badRequest().body("Failed to create a page");
        } catch (PageAlreadyExistException | PlotNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(path = "/addEffect", produces = "application/json")
    public ResponseEntity addEffect(@RequestBody JoinDTO joinDTO){
        try {
            pageService.addEffect(joinDTO);
            List<EffectDTO> effects = effectService.findAllByPage(joinDTO.getFirstId());
            return ResponseEntity.ok(effects);
        } catch (EffectNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(path = "/effects/add", produces = "application/json")
    public ResponseEntity createEffect(@RequestBody EffectDTO effectDTO){
        effectDTO = effectService.addEffect(effectDTO);
        if(effectDTO != null && effectDTO.getId() != 0)
            return ResponseEntity.ok(effectDTO);
        return ResponseEntity.badRequest().body("Failed to create an effect");
    }

    @PatchMapping(path = "/updateById/{id}", produces = "application/json")
    public ResponseEntity updatePageById(@PathVariable("id") Long pageId, @RequestBody PageDTO updatedPage){
        try {
            pageService.updateById(pageId, updatedPage);
            return ResponseEntity.ok("Page successfully updated");
        } catch (PageNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping(path = "/effects/update/{id}", produces = "application/json")
    public ResponseEntity updateEffect(@PathVariable("id") Long effectId, @RequestBody EffectDTO updatedEffect){
        try {
            effectService.update(effectId, updatedEffect);
            return ResponseEntity.ok("Effect successfully updated");
        } catch (EffectNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping(path = "/deleteById/{id}", produces = "application/json")
    public ResponseEntity deleteById(@PathVariable("id") Long pageId){
        try {
            pageService.deleteById(pageId);
            return ResponseEntity.ok("Page successfully deleted");
        } catch (PageNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping(path = "/effects/delete/{id}", produces = "application/json")
    public ResponseEntity deleteEffect(@PathVariable("id") Long effectId){
        try {
            effectService.delete(effectId);
            return ResponseEntity.ok("Effect successfully deleted");
        } catch (EffectNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
