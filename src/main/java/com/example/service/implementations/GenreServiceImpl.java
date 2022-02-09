package com.example.service.implementations;

import com.example.dto.PivotDTO.GenreDTO;
import com.example.entity.pivots.plot.Genres;
import com.example.exceptions.GenreAlreadyExistException;
import com.example.exceptions.GenreNotFoundException;
import com.example.repository.GenreRepository;
import com.example.service.interfaces.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    @Autowired
    public GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public GenreDTO addGenre(GenreDTO genreDTO) throws GenreAlreadyExistException {
        Genres genre = genreRepository.findByGenre(genreDTO.getName()).orElse(null);
        if(genre != null)
            throw new GenreAlreadyExistException("Genre with name: " + genreDTO.getName() + " already exist");
        genre = genreRepository.save(genreDTO.toGenre());
        return GenreDTO.fromGenre(genre);
    }

    @Override
    public List<GenreDTO> getAll() {
        List<Genres> genres = genreRepository.findAll();
        List<GenreDTO> genreDTOS = new ArrayList<>();
        genres.forEach(genre -> genreDTOS.add(GenreDTO.fromGenre(genre)));
        return genreDTOS;
    }

    @Override
    public GenreDTO findById(Long genreId) throws GenreNotFoundException {
        Genres genre = genreRepository.findById(genreId).orElse(null);
        if(genre == null)
            throw new GenreNotFoundException("Genre with id: " + genreId + " not found");
        return GenreDTO.fromGenre(genre);
    }

    @Override
    public GenreDTO findByName(String name) throws GenreNotFoundException {
        Genres genre = genreRepository.findByGenre(name).orElse(null);
        if(genre == null)
            throw new GenreNotFoundException("Genre with name: " + name + " not found");
        return GenreDTO.fromGenre(genre);
    }

    @Override
    public List<GenreDTO> findAllGenres(Long plotId) throws GenreNotFoundException {
        List<Genres> genres = genreRepository.findPlotGenres(plotId);
        if(genres.size() == 0)
            throw new GenreNotFoundException("Plot with id: " + plotId + " does not have any genres");
        List<GenreDTO> result = new ArrayList<>();
        genres.forEach(g -> result.add(GenreDTO.fromGenre(g)));
        return result;
    }

    @Override
    public void updateById(Long genreId, GenreDTO updatedGenre) throws GenreNotFoundException {
        GenreDTO genre = this.findById(genreId);
        genre.checkFields(updatedGenre);
        genreRepository.setInfoById(genreId, genre.getName(), genre.getDescription());
    }

    @Override
    public void updateByName(String name, GenreDTO updatedGenre) throws GenreNotFoundException{
        GenreDTO genre = this.findByName(name);
        genre.checkFields(updatedGenre);
        genreRepository.setInfoByName(name, genre.getDescription());
    }

    @Override
    public void deleteById(Long genreId) throws GenreNotFoundException {
        GenreDTO genre = this.findById(genreId);
        genreRepository.deleteFromPlots(genre.getId());
        genreRepository.deleteById(genre.getId());
    }

    @Override
    public void deleteByName(String name) throws GenreNotFoundException {
        GenreDTO genre = this.findByName(name);
        genreRepository.deleteFromPlots(genre.getId());
        genreRepository.deleteByName(genre.getName());
    }

}
