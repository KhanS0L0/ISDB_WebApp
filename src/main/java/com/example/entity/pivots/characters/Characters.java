package com.example.entity.pivots.characters;


import com.example.entity.enums.CharactersType;
import com.example.entity.pivots.processes.Process;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity(name = "CHARACTERS")
@Data
public class Characters {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "AGE")
    private int age;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "CHARACTER_TYPE")
    @Enumerated(EnumType.STRING)
    private CharactersType charactersType;

    @ManyToMany
    @JoinTable(
            name = "ABILITIES_CHARACTERS",
            joinColumns = @JoinColumn(name = "CHARACTER_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "ABILITY_ID", referencedColumnName = "ID")
    )
    private List<Ability> abilities;

//    @ManyToMany
//    @JoinTable(
//            name = "PROCESSES_CHARACTERS",
//            joinColumns = @JoinColumn(name = "CHARACTER_ID", referencedColumnName = "ID"),
//            inverseJoinColumns = @JoinColumn(name = "PROCESS_ID", referencedColumnName = "ID")
//    )
//    private List<Process> processes;
}
