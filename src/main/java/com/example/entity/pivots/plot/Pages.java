package com.example.entity.pivots.plot;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity(name = "PAGES")
@Data
public class Pages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "FRAME_COUNT")
    private int frameCount;

    @Column(name = "DESCRIPTION")
    private String description;

    @ManyToOne
    @JoinColumn(name = "PLOT_ID", referencedColumnName = "ID")
    private Plot plot;

    @ManyToMany
    @JoinTable(
            name = "PAGES_EFFECTS",
            joinColumns = @JoinColumn(name = "PAGE_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "EFFECT_ID", referencedColumnName = "ID")
    )
    private List<Effect> effects;
}
