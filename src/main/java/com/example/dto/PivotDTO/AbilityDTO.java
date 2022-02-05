package com.example.dto.PivotDTO;

import com.example.entity.enums.AbilityType;
import com.example.entity.pivots.characters.Ability;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
public class AbilityDTO {
    private Long id;
    private String type;
    private String description;

    public Ability toAbility(){
        Ability ability = new Ability();
        ability.setId(this.id);
        ability.setDescription(this.description);
        ability.setAbilityType(AbilityType.valueOf(this.type));
        return ability;
    }

    public static AbilityDTO fromAbility(Ability ability){
        AbilityDTO abilityDTO = new AbilityDTO();
        abilityDTO.setId(abilityDTO.getId());
        abilityDTO.setDescription(abilityDTO.getDescription());
        abilityDTO.setType(ability.getAbilityType().name());
        return abilityDTO;
    }


}
