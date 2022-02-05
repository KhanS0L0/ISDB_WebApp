package com.example.entity.pivots.processes;

import com.example.dto.ProcessDTO.ProcessDTO;
import com.example.entity.enums.ProcessStatus;
import com.example.entity.enums.ProcessType;
import com.example.entity.pivots.worker.Artist;
import com.example.entity.pivots.worker.Screenwriter;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity(name = "PROCESS")
@Data
public class Process {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "PROCESS_TYPE")
    @Enumerated(EnumType.STRING)
    private ProcessType processType;

    @Column(name = "PROCESS_STATUS")
    @Enumerated(EnumType.STRING)
    private ProcessStatus processStatus;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "BEGIN_DATE")
    private Timestamp beginDate;

    @Column(name = "DEADLINE")
    private Timestamp deadline;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "ARTISTS_PROCESSES",
            joinColumns = @JoinColumn(name = "PROCESS_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "ARTIST_ID", referencedColumnName = "ID")
    )
    private List<Artist> artists;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "SCREENWRITERS_PROCESSES",
            joinColumns = @JoinColumn(name = "PROCESS_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "SCREENWRITER_ID", referencedColumnName = "ID")
    )
    private List<Screenwriter> screenwriters;

    public Process(){}

    public Process(String processType,
                   String processStatus,
                   String description,
                   Timestamp beginDate,
                   Timestamp deadline){
        this.processType = ProcessType.valueOf(processType);
        this.processStatus = ProcessStatus.valueOf(processStatus);
        this.description = description;
        this.beginDate = beginDate;
        this.deadline = deadline;
    }

    public static Process create(ProcessDTO processDTO){
        return new Process(
                processDTO.getProcessType(),
                processDTO.getProcessStatus(),
                processDTO.getDescription(),
                processDTO.getBeginDate(),
                processDTO.getDeadline()
        );
    }

    /*
     todo: перенести в отдельный метод в Enum.ProcessType
      => при добавлении новых типов процессов не будет переписываться логика метода
      => проходиться по уже имеющимся перечислениям
    */

    public static String isArtistOrScreenwriterProcess(ProcessDTO processDTO){
        String type = processDTO.getProcessType();
        if(type != null && (
                type.equals(ProcessType.CHARACTER_DRAWING.name()) ||
                type.equals(ProcessType.PAGE_DRAWING.name()) ||
                type.equals(ProcessType.PAGE_REFACTORING.name()) ||
                type.equals(ProcessType.TEXT_DRAWING.name())
            )
        ){
            return "Artist";
        }else if(type != null && (
                        type.equals(ProcessType.PLOT_CREATING.name()) ||
                        type.equals(ProcessType.CHARACTER_CREATING.name()) ||
                        type.equals(ProcessType.ABILITY_CREATING.name()) ||
                        type.equals(ProcessType.PAGE_CREATING.name()) ||
                        type.equals(ProcessType.TEXT_WRITING.name())
                )
        ){
            return "Screenwriter";
        }
        return null;
    }

}