package com.example.dto.PivotDTO;

import com.example.entity.enums.ShellType;
import com.example.entity.pivots.plot.Pages;
import com.example.entity.pivots.plot.Text;
import lombok.Data;

@Data
public class TextDTO {

    private Long textId;
    private Long pageId;
    private String description;
    private String shellType;
    private String font;
    private int size;

    public Text toText(Pages page){
        Text text = new Text();
        text.setId(this.getTextId());
        text.setDescription(this.getDescription());
        text.setFont(this.getFont());
        text.setSize(this.getSize());
        text.setShellType(ShellType.valueOf(this.getShellType()));
        text.setPage(page);
        return text;
    }

    public static TextDTO fromText(Text text){
        TextDTO textDTO = new TextDTO();
        textDTO.setTextId(text.getId());
        textDTO.setDescription(text.getDescription());
        textDTO.setFont(text.getFont());
        textDTO.setSize(text.getSize());
        textDTO.setShellType(text.getShellType().name());
        if(text.getPage() == null)
            textDTO.setPageId(0L);
        else
            textDTO.setPageId(text.getPage().getId());
        return textDTO;
    }

    public void checkFields(TextDTO updatedText){
        if(updatedText.getPageId() != 0)
            this.setPageId(updatedText.getPageId());
        if(updatedText.getDescription() != null && !updatedText.getDescription().isEmpty())
            this.setDescription(updatedText.getDescription());
        if(updatedText.getShellType() != null && !updatedText.getShellType().isEmpty())
            this.setShellType(updatedText.getShellType());
        if(updatedText.getFont() != null && !updatedText.getFont().isEmpty())
            this.setFont(updatedText.getFont());
        if(updatedText.getSize() != 0)
            this.setSize(updatedText.getSize());
    }
}
