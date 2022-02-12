package com.example.service.implementations;

import com.example.dto.PivotDTO.CharacterDTO;
import com.example.dto.PivotDTO.GenreDTO;
import com.example.dto.PivotDTO.PlotDTO;
import com.example.entity.pivots.characters.Characters;
import com.example.entity.pivots.plot.Plot;
import com.example.exceptions.notFoundExceptions.CharacterNotFoundException;
import com.example.exceptions.notFoundExceptions.GenreNotFoundException;
import com.example.exceptions.alreadyExistExceptions.PlotAlreadyExistException;
import com.example.exceptions.notFoundExceptions.PlotNotFoundException;
import com.example.repository.PlotRepository;
import com.example.service.interfaces.GenreService;
import com.example.service.interfaces.PlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlotServiceImpl implements PlotService {

    private final PlotRepository plotRepository;
    private final GenreService genreService;

    @Autowired
    public PlotServiceImpl(PlotRepository plotRepository, GenreService genreService) {
        this.plotRepository = plotRepository;
        this.genreService = genreService;
    }

    @Override
    public PlotDTO addPlot(PlotDTO plotDTO) throws PlotAlreadyExistException {
        Plot plot = plotRepository.findByDescription(plotDTO.getDescription()).orElse(null);
        if(plot != null)
            throw new PlotAlreadyExistException("Plot already exist");
        plot = plotRepository.save(plotDTO.toPlot());
        return PlotDTO.fromPlot(plot);
    }

    @Override
    public List<PlotDTO> getAll() {
        List<Plot> plots = plotRepository.findAll();
        List<PlotDTO> plotDTOS = new ArrayList<>();
        plots.forEach(plot -> plotDTOS.add(PlotDTO.fromPlot(plot)));
        return plotDTOS;
    }

    @Override
    public List<CharacterDTO> getAllPlotCharacters(Long plotId) throws CharacterNotFoundException {
        List<Characters> characters = plotRepository.findAllCharacter(plotId);
        if(characters.size() == 0)
            throw new CharacterNotFoundException("Plot does not have any characters");
        List<CharacterDTO> result = new ArrayList<>();
        characters.forEach(characters1 -> result.add(CharacterDTO.fromCharacter(characters1)));
        return result;
    }

    @Override
    public PlotDTO findById(Long plotId) throws PlotNotFoundException {
        Plot plot = plotRepository.findById(plotId).orElse(null);
        if(plot == null)
            throw new PlotNotFoundException("Plot with id: " + plotId + " not found");
        return PlotDTO.fromPlot(plot);
    }

    @Override
    public void addGenre(Long plotId, Long genreId) throws PlotNotFoundException, GenreNotFoundException {
        PlotDTO plot = this.findById(plotId);
        GenreDTO genre = genreService.findById(genreId);
        plotRepository.addGenre(plot.getId(), genre.getId());
    }

    @Override
    public void update(Long plotId, PlotDTO plotDTO) throws PlotNotFoundException {
        PlotDTO plot = this.findById(plotId);
        plot.checkFields(plotDTO);
        plotRepository.setPlotInfoById(plot.getId(), plot.getDescription(), plot.getArc());
    }

    @Override
    public void delete(Long plotId) throws PlotNotFoundException {
        PlotDTO plot = this.findById(plotId);
        plotRepository.deleteFromGenre(plot.getId());
        plotRepository.delete(plot.getId());
    }
}
