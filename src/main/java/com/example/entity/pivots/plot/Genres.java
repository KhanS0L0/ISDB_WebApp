package com.example.entity.pivots.plot;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity(name = "GENRES")
@Data
public class Genres {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "GENRE")
    private String genre;

    @Column(name = "DESCRIPTION")
    private String description;

    @ManyToMany
    @JoinTable(
            name = "PLOT_GENRES",
            joinColumns = @JoinColumn(name = "GENRE_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "PLOT_ID", referencedColumnName = "ID")
    )
    private List<Plot> plots;

}
