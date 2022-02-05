package com.example.dto.ProcessDTO;

import com.example.entity.pivots.processes.Process;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class ProcessDTO {
    private Long id;
    private String processType;
    private String processStatus;
    private String description;
    private Timestamp beginDate;
    private Timestamp deadline;

    public ProcessDTO(){}
    public ProcessDTO(Long id,
                      String processType,
                      String processStatus,
                      String description,
                      Timestamp beginDate,
                      Timestamp deadline) {
        this.id = id;
        this.processType = processType;
        this.processStatus = processStatus;
        this.description = description;
        this.beginDate = beginDate;
        this.deadline = deadline;
    }

    public ProcessDTO toProcessDTO(Process process){
        return new ProcessDTO(
                this.id = process.getId(),
                this.processType = process.getProcessType().name(),
                this.processStatus = process.getProcessStatus().name(),
                this.description = process.getDescription(),
                this.beginDate = process.getBeginDate(),
                this.deadline = process.getDeadline()
        );
    }

    public void checkFields(ProcessDTO updatedProcess){
        if(updatedProcess.getProcessType() != null && !updatedProcess.getProcessType().isEmpty())
            this.setProcessType(updatedProcess.getProcessType());
        if(updatedProcess.getProcessStatus() != null && !updatedProcess.getProcessStatus().isEmpty())
            this.setProcessStatus(updatedProcess.getProcessStatus());
        if(updatedProcess.getDescription() != null && !updatedProcess.getDescription().isEmpty())
            this.setDescription(updatedProcess.getDescription());
        if(updatedProcess.getBeginDate() != null)
            this.setBeginDate(updatedProcess.getBeginDate());
        if(updatedProcess.getDeadline() != null)
            this.setDeadline(updatedProcess.getDeadline());
    }

}
