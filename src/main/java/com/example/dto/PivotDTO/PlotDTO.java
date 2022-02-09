package com.example.dto.PivotDTO;

import com.example.entity.pivots.plot.Plot;
import lombok.Data;

@Data
public class PlotDTO {
    private Long id;
    private String description;
    private int arc;

    public Plot toPlot(){
        Plot plot = new Plot();
        plot.setId(this.getId());
        plot.setArc(this.getArc());
        plot.setDescription(this.getDescription());
        return plot;
    }

    public static PlotDTO fromPlot(Plot plot){
        PlotDTO plotDTO = new PlotDTO();
        plotDTO.setId(plot.getId());
        plotDTO.setArc(plot.getArc());
        plotDTO.setDescription(plot.getDescription());
        return plotDTO;
    }

    public void checkFields(PlotDTO updatedPlot){
        if(updatedPlot.getArc() != 0)
            this.setArc(updatedPlot.getArc());
        if(updatedPlot.getDescription() != null && !updatedPlot.getDescription().isEmpty())
            this.setDescription(updatedPlot.getDescription());
    }

}
