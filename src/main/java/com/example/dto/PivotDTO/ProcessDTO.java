package com.example.dto.PivotDTO;

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
}
