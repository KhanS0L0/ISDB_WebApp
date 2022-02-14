package com.example.entity.pivots.plot;

import com.example.entity.enums.ShellType;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity(name = "TEXT")
@Data
public class Text {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PAGE_ID", referencedColumnName = "ID")
    private Pages page;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "SHELL_TYPE")
    @Enumerated(EnumType.STRING)
    private ShellType shellType;

    @Column(name = "SIZE")
    private int size;

    @Column(name = "FONT")
    private String font;

    @ManyToMany
    @JoinTable(
            name = "TEXTS_EFFECTS",
            joinColumns = @JoinColumn(name = "TEXT_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "EFFECT_ID", referencedColumnName = "ID")
    )
    private List<Effect> effects;
}
