package com.example.service.interfaces;

import com.example.dto.PivotDTO.GenreDTO;
import com.example.dto.PivotDTO.PlotDTO;
import com.example.exceptions.GenreNotFoundException;
import com.example.exceptions.PlotAlreadyExistException;
import com.example.exceptions.PlotNotFoundException;

import java.util.List;

public interface PlotService {

    PlotDTO addPlot(PlotDTO plotDTO) throws PlotAlreadyExistException;

    List<PlotDTO> getAll();

    PlotDTO findById(Long plotId) throws PlotNotFoundException;

    void addGenre(Long plotId, Long genreId) throws PlotNotFoundException, GenreNotFoundException;

    void update(Long plotId, PlotDTO plotDTO) throws PlotNotFoundException;

    void delete(Long plotId) throws PlotNotFoundException;

}
