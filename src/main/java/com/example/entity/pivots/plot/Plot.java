package com.example.entity.pivots.plot;

import com.example.entity.pivots.characters.Characters;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity(name = "PLOT")
@Data
public class Plot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "NUMBER_OF_ARC")
    private int arc;

    @ManyToMany
    @JoinTable(
            name = "PLOT_GENRES",
            joinColumns = @JoinColumn(name = "PLOT_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "GENRE_ID", referencedColumnName = "ID")
    )
    private List<Genres> genres;

    @ManyToMany
    @JoinTable(
            name = "PLOTS_CHARACTERS",
            joinColumns = @JoinColumn(name = "PLOT_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "CHARACTER_ID", referencedColumnName = "ID")
    )
    private List<Characters> characters;

    @OneToMany(mappedBy = "plot")
    private List<Pages> pages;
}
