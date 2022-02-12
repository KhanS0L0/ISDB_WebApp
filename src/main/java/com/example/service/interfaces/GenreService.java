package com.example.service.interfaces;

import com.example.dto.PivotDTO.GenreDTO;
import com.example.exceptions.alreadyExistExceptions.GenreAlreadyExistException;
import com.example.exceptions.notFoundExceptions.GenreNotFoundException;

import java.util.List;

public interface GenreService {

    GenreDTO addGenre(GenreDTO genreDTO) throws GenreAlreadyExistException;

    List<GenreDTO> getAll();

    GenreDTO findById(Long genreId) throws GenreNotFoundException;

    GenreDTO findByName(String name) throws GenreNotFoundException;

    List<GenreDTO> findAllGenres(Long plotId) throws GenreNotFoundException;

    void updateById(Long genreId, GenreDTO genreDTO) throws GenreNotFoundException;

    void updateByName(String name, GenreDTO genreDTO) throws GenreNotFoundException;

    void deleteById(Long genreId) throws GenreNotFoundException;

    void deleteByName(String name) throws GenreNotFoundException;

}
