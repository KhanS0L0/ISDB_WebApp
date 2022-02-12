package com.example.dto.PivotDTO;

import com.example.entity.pivots.plot.Pages;
import com.example.entity.pivots.plot.Plot;
import lombok.Data;

@Data
public class PageDTO {
    private Long id;
    private Long plotId;
    private int frame;
    private String description;

    public Pages toPage(Plot plot){
        Pages pages = new Pages();
        pages.setId(this.getId());
        pages.setDescription(this.getDescription());
        pages.setFrameCount(this.getFrame());
        pages.setPlot(plot);
        return pages;
    }

    public static PageDTO fromPage(Pages page){
        PageDTO result = new PageDTO();
        result.setId(page.getId());
        result.setDescription(page.getDescription());
        result.setFrame(page.getFrameCount());
        result.setPlotId(page.getPlot().getId());
        return result;
    }

    public void checkFields(PageDTO updatedPage){
        if(updatedPage.getDescription() != null && !updatedPage.getDescription().isEmpty())
            this.setDescription(updatedPage.getDescription());
        if(updatedPage.getFrame() != 0)
            this.setFrame(updatedPage.getFrame());
        if(updatedPage.getPlotId() != null && updatedPage.getPlotId() != 0)
            this.setPlotId(updatedPage.getPlotId());
    }
}