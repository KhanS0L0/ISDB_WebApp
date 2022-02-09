package com.example.dto.PivotDTO;

import com.example.entity.enums.AbilityType;
import com.example.entity.pivots.characters.Ability;
import lombok.Data;

@Data
public class AbilityDTO {
    private Long id;
    private String type;
    private String description;

    public Ability toAbility(){
        Ability ability = new Ability();
        ability.setId(this.getId());
        ability.setDescription(this.getDescription());
        ability.setAbilityType(AbilityType.valueOf(this.type));
        return ability;
    }

    public static AbilityDTO fromAbility(Ability ability){
        AbilityDTO abilityDTO = new AbilityDTO();
        abilityDTO.setId(ability.getId());
        abilityDTO.setDescription(ability.getDescription());
        abilityDTO.setType(ability.getAbilityType().name());
        return abilityDTO;
    }

    public void checkFields(AbilityDTO updatedAbility){
        if(updatedAbility.getType() != null && !updatedAbility.getType().isEmpty())
            this.setType(updatedAbility.getType());
        if(updatedAbility.getDescription() != null && !updatedAbility.getDescription().isEmpty())
            this.setDescription(updatedAbility.getDescription());
    }

}
