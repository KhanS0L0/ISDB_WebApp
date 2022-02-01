package com.example.entity.pivots.characters;


import com.example.entity.enums.AbilityType;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity(name = "ABILITY")
@Data
public class Ability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "ABILITY_TYPE")
    @Enumerated(EnumType.STRING)
    private AbilityType abilityType;

    @ManyToMany
    @JoinTable(
            name = "ABILITIES_CHARACTERS",
            joinColumns = @JoinColumn(name = "ABILITY_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "CHARACTER_ID", referencedColumnName = "ID")
    )
    private List<Characters> characters;

}
