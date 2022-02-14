package com.example.entity.pivots.plot;

import com.example.entity.enums.EffectType;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity(name = "EFFECT")
@Data
public class Effect {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "TYPE")
    @Enumerated(EnumType.STRING)
    private EffectType effectType;

    @ManyToMany
    @JoinTable(
            name = "PAGES_EFFECTS",
            joinColumns = @JoinColumn(name = "EFFECT_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "PAGE_ID", referencedColumnName = "ID")
    )
    private List<Pages> pages;


    @ManyToMany
    @JoinTable(
            name = "TEXTS_EFFECTS",
            joinColumns = @JoinColumn(name = "EFFECT_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "TEXT_ID", referencedColumnName = "ID")
    )
    private List<Text> texts;
}
