package com.example.dto.PivotDTO;

import com.example.entity.pivots.characters.Ability;
import lombok.Data;

import java.util.List;

@Data
public class CharacterDTO {
    private Long id;
    private String name;
    private String age;
    private String description;
    private String type;
    private List<Ability> abilityList;
}
