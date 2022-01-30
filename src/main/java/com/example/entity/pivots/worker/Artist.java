package com.example.entity.pivots.worker;

import com.example.entity.pivots.processes.Process;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity(name = "ARTIST")
@Data
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "WORKER_ID", referencedColumnName = "ID")
    private Worker worker;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "ARTISTS_PROCESSES",
            joinColumns = @JoinColumn(name = "ARTIST_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "PROCESS_ID", referencedColumnName = "ID")
    )
    private List<Process> processes;

    public Artist() {
    }

    public Artist(Worker worker){
        this.worker = worker;
    }
}
