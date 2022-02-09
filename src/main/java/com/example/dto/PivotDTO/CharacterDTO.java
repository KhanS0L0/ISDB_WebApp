package com.example.dto.PivotDTO;

import com.example.entity.enums.CharactersType;
import com.example.entity.pivots.characters.Characters;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CharacterDTO {
    private Long id;
    private String name;
    private int age;
    private String description;
    private String type;

    public Characters toCharacter(){
        Characters character = new Characters();
        character.setId(this.getId());
        character.setName(this.getName());
        character.setAge(this.getAge());
        character.setDescription(this.getDescription());
        character.setCharactersType(CharactersType.valueOf(this.getType()));
        return character;
    }

    public static CharacterDTO fromCharacter(Characters character){
        CharacterDTO characterDTO = new CharacterDTO();
        characterDTO.setId(character.getId());
        characterDTO.setName(character.getName());
        characterDTO.setAge(character.getAge());
        characterDTO.setDescription(character.getDescription());
        characterDTO.setType(character.getCharactersType().name());
        return characterDTO;
    }

    public void checkFields(CharacterDTO updatedCharacter){
        if(updatedCharacter.getName() != null && !updatedCharacter.getName().isEmpty())
            this.setName(updatedCharacter.getName());
        if(updatedCharacter.getAge() != 0)
            this.setAge(updatedCharacter.getAge());
        if(updatedCharacter.getDescription() != null && !updatedCharacter.getDescription().isEmpty())
            this.setDescription(updatedCharacter.getDescription());
        if(updatedCharacter.getType() != null && !updatedCharacter.getType().isEmpty())
            this.setDescription(updatedCharacter.getDescription());
    }
}