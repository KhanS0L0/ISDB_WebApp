package com.example.controller;

import com.example.dto.JoinDTO;
import com.example.dto.PivotDTO.CharacterDTO;
import com.example.dto.PivotDTO.GenreDTO;
import com.example.dto.PivotDTO.PlotDTO;
import com.example.exceptions.alreadyExistExceptions.GenreAlreadyExistException;
import com.example.exceptions.alreadyExistExceptions.PlotAlreadyExistException;
import com.example.exceptions.notFoundExceptions.CharacterNotFoundException;
import com.example.exceptions.notFoundExceptions.GenreNotFoundException;
import com.example.exceptions.notFoundExceptions.PlotNotFoundException;
import com.example.service.interfaces.GenreService;
import com.example.service.interfaces.PlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/plotGenres")
public class PlotController {

    private final PlotService plotService;
    private final GenreService genreService;

    @Autowired
    public PlotController(PlotService plotService, GenreService genreService) {
        this.plotService = plotService;
        this.genreService = genreService;
    }

    @GetMapping(path = "/all/plots", produces = "application/json")
    public ResponseEntity getAllPlots(){
        List<PlotDTO> response = plotService.getAll();
        if(response.size() == 0)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Plots do not exist");
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/all/genres", produces = "application/json")
    public ResponseEntity getAllGenres(){
        List<GenreDTO> response = genreService.getAll();
        if(response.size() == 0)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Genres do not exist");
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/plots/{id}", produces = "application/json")
    public ResponseEntity findPlotById(@PathVariable("id") Long plotId){
        try {
            PlotDTO plot = plotService.findById(plotId);
            return ResponseEntity.ok(plot);
        } catch (PlotNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping(path = "/plots/{id}/genres", produces = "application/json")
    public ResponseEntity findPlotGenres(@PathVariable("id") Long plotId){
        try {
            List<GenreDTO> genres = genreService.findAllGenres(plotId);
            return ResponseEntity.ok(genres);
        } catch (GenreNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(path = "/plots/{id}/characters", produces = "application/json")
    public ResponseEntity findPlotCharacters(@PathVariable("id") Long plotId){
        try {
            List<CharacterDTO> response = plotService.getAllPlotCharacters(plotId);
            return ResponseEntity.ok(response);
        } catch (CharacterNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(path = "/genres/id/{id}", produces = "application/json")
    public ResponseEntity findGenreById(@PathVariable("id") Long genreId){
        try {
            GenreDTO genre = genreService.findById(genreId);
            return ResponseEntity.ok(genre);
        } catch (GenreNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping(path = "/genres/name/{name}", produces = "application/json")
    public ResponseEntity findGenreByName(@PathVariable("name") String name){
        try {
            GenreDTO genre = genreService.findByName(name);
            return ResponseEntity.ok(genre);
        } catch (GenreNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping(path = "/addPlot", produces = "application/json")
    public ResponseEntity addPlot(@RequestBody PlotDTO plotDTO){
        try {
            plotDTO = plotService.addPlot(plotDTO);
            if(plotDTO != null && plotDTO.getId() != null)
                return ResponseEntity.status(HttpStatus.CREATED).body(plotDTO);
            return ResponseEntity.badRequest().body("Failed to create plot");
        } catch (PlotAlreadyExistException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(path = "/addGenre", produces = "application/json")
    public ResponseEntity addGenre(@RequestBody GenreDTO genreDTO){
        try {
            genreDTO = genreService.addGenre(genreDTO);
            if(genreDTO != null && genreDTO.getId() != null)
                return ResponseEntity.ok(genreDTO);
            return ResponseEntity.badRequest().body("Failed to create genre");
        } catch (GenreAlreadyExistException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(path = "/joinGenre", produces = "application/json")
    public ResponseEntity joinGenreToPlot(@RequestBody JoinDTO joinDTO){
        try {
            plotService.addGenre(joinDTO.getFirstId(), joinDTO.getSecondId());
            return ResponseEntity.ok("Genre successfully added to plot");
        } catch (PlotNotFoundException | GenreNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping(path = "/updatePlot/{id}", produces = "application/json")
    public ResponseEntity updatePlot(@PathVariable("id") Long plotId, @RequestBody PlotDTO plotDTO){
        try {
            plotService.update(plotId, plotDTO);
            return ResponseEntity.ok("Plot successfully updated");
        } catch (PlotNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping(path = "/updateGenreById/{id}", produces = "application/json")
    public ResponseEntity updateGenreById(@PathVariable("id") Long genreId, @RequestBody GenreDTO genreDTO){
        try {
            genreService.updateById(genreId, genreDTO);
            return ResponseEntity.ok("Genre successfully updated");
        } catch (GenreNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping(path = "/updateGenreByName/{name}", produces = "application/json")
    public ResponseEntity updateGenreByName(@PathVariable("name") String name, @RequestBody GenreDTO genreDTO){
        try {
            genreService.updateByName(name, genreDTO);
            return ResponseEntity.ok("Genre successfully updated");
        } catch (GenreNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping(path = "/deletePlot/{id}", produces = "application/json")
    public ResponseEntity deletePlot(@PathVariable("id") Long plotId){
        try {
            plotService.delete(plotId);
            return ResponseEntity.ok("Plot successfully deleted");
        } catch (PlotNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping(path = "/deleteGenreById/{id}", produces = "application/json")
    public ResponseEntity deleteGenreById(@PathVariable("id") Long genreId){
        try {
            genreService.deleteById(genreId);
            return ResponseEntity.ok("Genre successfully deleted");
        } catch (GenreNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping(path = "/deleteGenreByName/{name}", produces = "application/json")
    public ResponseEntity deleteGenreByName(@PathVariable("name") String name){
        try {
            genreService.deleteByName(name);
            return ResponseEntity.ok("Genre successfully deleted");
        } catch (GenreNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
