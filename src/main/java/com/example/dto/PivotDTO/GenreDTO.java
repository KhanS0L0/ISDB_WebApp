package com.example.dto.PivotDTO;

import com.example.entity.pivots.plot.Genres;
import lombok.Data;

@Data
public class GenreDTO {
    private Long id;
    private String name;
    private String description;

    public Genres toGenre(){
        Genres genre = new Genres();
        genre.setId(this.getId());
        genre.setGenre(this.getName());
        genre.setDescription(this.getDescription());
        return genre;
    }

    public static GenreDTO fromGenre(Genres genre){
        GenreDTO genreDTO = new GenreDTO();
        genreDTO.setId(genre.getId());
        genreDTO.setName(genre.getGenre());
        genreDTO.setDescription(genre.getDescription());
        return genreDTO;
    }

    public void checkFields(GenreDTO updatedGenre){
        if(updatedGenre.getName() != null && !updatedGenre.getName().isEmpty())
            this.setName(updatedGenre.getName());
        if(updatedGenre.getDescription() != null && !updatedGenre.getDescription().isEmpty())
            this.setDescription(updatedGenre.getDescription());
    }

}
