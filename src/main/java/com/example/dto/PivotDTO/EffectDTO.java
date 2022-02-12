package com.example.dto.PivotDTO;

import com.example.entity.enums.EffectType;
import com.example.entity.pivots.plot.Effect;
import lombok.Data;

@Data
public class EffectDTO {
    private Long id;
    private String type;
    private String description;

    public Effect toEffect(){
        Effect effect = new Effect();
        effect.setId(this.getId());
        effect.setEffectType(EffectType.valueOf(this.getType()));
        effect.setDescription(this.getDescription());
        return effect;
    }

    public static EffectDTO fromEffect(Effect effect){
        EffectDTO result = new EffectDTO();
        result.setId(effect.getId());
        result.setDescription(effect.getDescription());
        result.setType(effect.getEffectType().name());
        return result;
    }

    public void checkFields(EffectDTO updatedEffect){
        if(updatedEffect.getDescription() != null && !updatedEffect.getDescription().isEmpty())
            this.setDescription(updatedEffect.getDescription());
        if(updatedEffect.getType() != null && !updatedEffect.getType().isEmpty())
            this.setType(updatedEffect.getType());
    }
}
