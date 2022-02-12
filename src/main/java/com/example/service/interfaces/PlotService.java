package com.example.service.interfaces;

import com.example.dto.PivotDTO.CharacterDTO;
import com.example.dto.PivotDTO.PlotDTO;
import com.example.exceptions.notFoundExceptions.CharacterNotFoundException;
import com.example.exceptions.notFoundExceptions.GenreNotFoundException;
import com.example.exceptions.alreadyExistExceptions.PlotAlreadyExistException;
import com.example.exceptions.notFoundExceptions.PlotNotFoundException;

import java.util.List;

public interface PlotService {

    PlotDTO addPlot(PlotDTO plotDTO) throws PlotAlreadyExistException;

    List<PlotDTO> getAll();

    List<CharacterDTO> getAllPlotCharacters(Long plotId) throws CharacterNotFoundException;

    PlotDTO findById(Long plotId) throws PlotNotFoundException;

    void addGenre(Long plotId, Long genreId) throws PlotNotFoundException, GenreNotFoundException;

    void update(Long plotId, PlotDTO plotDTO) throws PlotNotFoundException;

    void delete(Long plotId) throws PlotNotFoundException;

}
