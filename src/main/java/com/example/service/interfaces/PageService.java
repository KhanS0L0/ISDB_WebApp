package com.example.service.interfaces;

import com.example.dto.JoinDTO;
import com.example.dto.PivotDTO.PageDTO;
import com.example.exceptions.alreadyExistExceptions.PageAlreadyExistException;
import com.example.exceptions.notFoundExceptions.PageNotFoundException;
import com.example.exceptions.notFoundExceptions.PlotNotFoundException;

import java.util.List;

public interface PageService {

    PageDTO addPage(PageDTO page) throws PageAlreadyExistException, PlotNotFoundException;

    void addEffect(JoinDTO joinDTO);

    List<PageDTO> getAll() throws PageNotFoundException;

    PageDTO findById(Long pageId) throws PageNotFoundException;

    PageDTO findByFrameCount(int frame, Long plotId) throws PageNotFoundException;

    List<PageDTO> findAllByPlot(Long plotId) throws PageNotFoundException;

    void updateById(Long pageId, PageDTO pageDTO) throws PageNotFoundException;

    void deleteById(Long pageId) throws PageNotFoundException;

}
