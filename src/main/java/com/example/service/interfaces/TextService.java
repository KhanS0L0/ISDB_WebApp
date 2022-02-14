package com.example.service.interfaces;

import com.example.dto.JoinDTO;
import com.example.dto.PivotDTO.TextDTO;
import com.example.entity.enums.ShellType;
import com.example.exceptions.alreadyExistExceptions.TextAlreadyExistException;
import com.example.exceptions.notFoundExceptions.EffectNotFoundException;
import com.example.exceptions.notFoundExceptions.PageNotFoundException;
import com.example.exceptions.notFoundExceptions.TextNotFoundException;

import java.util.List;

public interface TextService {

    TextDTO addText(TextDTO textDTO) throws TextAlreadyExistException, PageNotFoundException;

    void addEffect(JoinDTO joinDTO) throws TextNotFoundException, EffectNotFoundException;

    List<TextDTO> getAll() throws TextNotFoundException;

    TextDTO findById(Long textId) throws TextNotFoundException;

    List<TextDTO> findAllByType(ShellType type) throws TextNotFoundException;

    void update(Long textId, TextDTO updatedText) throws TextNotFoundException;

    void delete(Long textId) throws TextNotFoundException;

}
