package com.example.service.implementations;

import com.example.dto.JoinDTO;
import com.example.dto.PivotDTO.PageDTO;
import com.example.entity.pivots.plot.Pages;
import com.example.entity.pivots.plot.Plot;
import com.example.exceptions.alreadyExistExceptions.PageAlreadyExistException;
import com.example.exceptions.notFoundExceptions.PageNotFoundException;
import com.example.exceptions.notFoundExceptions.PlotNotFoundException;
import com.example.repository.PageRepository;
import com.example.repository.PlotRepository;
import com.example.service.interfaces.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PageServiceImpl implements PageService {

    private final PageRepository pageRepository;
    private final PlotRepository plotRepository;

    @Autowired
    public PageServiceImpl(PageRepository pageRepository, PlotRepository plotRepository) {
        this.pageRepository = pageRepository;
        this.plotRepository = plotRepository;
    }

    @Override
    public PageDTO addPage(PageDTO page) throws PageAlreadyExistException, PlotNotFoundException {
        Pages result = pageRepository.findByFrameCount(page.getFrame(), page.getPlotId()).orElse(null);
        if(result != null)
            throw new PageAlreadyExistException("Page with frame count: " + page.getFrame() + " already exist");
        Plot plot = plotRepository.findById(page.getPlotId()).orElse(null);
        if(plot == null)
            throw new PlotNotFoundException("Cant join page to plot with id: " + page.getPlotId() + ". Plot doesnt exist");
        result = pageRepository.save(page.toPage(plot));
        return PageDTO.fromPage(result);
    }

    @Override
    public void addEffect(JoinDTO joinDTO) {
        pageRepository.addEffect(joinDTO.getFirstId(), joinDTO.getSecondId());
    }

    @Override
    public List<PageDTO> getAll() throws PageNotFoundException {
        List<Pages> pages = pageRepository.findAll();
        if(pages.size() == 0)
            throw new PageNotFoundException("Pages do not exist");
        List<PageDTO> result = new ArrayList<>();
        pages.forEach(page -> result.add(PageDTO.fromPage(page)));
        return result;
    }

    @Override
    public PageDTO findById(Long pageId) throws PageNotFoundException {
        Pages page = pageRepository.findById(pageId).orElse(null);
        if(page == null)
            throw new PageNotFoundException("Page with id: " + pageId + " not found");
        return PageDTO.fromPage(page);
    }

    @Override
    public PageDTO findByFrameCount(int frame, Long plotId) throws PageNotFoundException {
        Pages page = pageRepository.findByFrameCount(frame, plotId).orElse(null);
        if(page == null)
            throw new PageNotFoundException("Page with frame count: " + frame + " not found");
        return PageDTO.fromPage(page);
    }

    @Override
    public List<PageDTO> findAllByPlot(Long plotId) throws PageNotFoundException {
        List<Pages> pages = pageRepository.findAllByPlot(plotId);
        if(pages.size() == 0)
            throw new PageNotFoundException("Plot does not have any pages");
        List<PageDTO> result = new ArrayList<>();
        pages.forEach(page -> result.add(PageDTO.fromPage(page)));
        return result;
    }

    @Override
    public void updateById(Long pageId, PageDTO updatedPage) throws PageNotFoundException {
        PageDTO page = this.findById(pageId);
        page.checkFields(updatedPage);
        pageRepository.updateById(page.getId(), page.getPlotId(), page.getFrame(), page.getDescription());
    }

    @Override
    public void deleteById(Long pageId) throws PageNotFoundException {
        this.findById(pageId);
        pageRepository.deleteFromEffects(pageId);
        pageRepository.deleteById(pageId);
    }

}
