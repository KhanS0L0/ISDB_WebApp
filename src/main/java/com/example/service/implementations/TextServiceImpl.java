package com.example.service.implementations;

import com.example.dto.JoinDTO;
import com.example.dto.PivotDTO.TextDTO;
import com.example.entity.enums.ShellType;
import com.example.entity.pivots.plot.Effect;
import com.example.entity.pivots.plot.Pages;
import com.example.entity.pivots.plot.Text;
import com.example.exceptions.alreadyExistExceptions.TextAlreadyExistException;
import com.example.exceptions.notFoundExceptions.EffectNotFoundException;
import com.example.exceptions.notFoundExceptions.PageNotFoundException;
import com.example.exceptions.notFoundExceptions.TextNotFoundException;
import com.example.repository.EffectRepository;
import com.example.repository.PageRepository;
import com.example.repository.TextRepository;
import com.example.service.interfaces.TextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TextServiceImpl implements TextService {

    private final TextRepository textRepository;
    private final PageRepository pageRepository;
    private final EffectRepository effectRepository;

    @Autowired
    public TextServiceImpl(TextRepository textRepository, PageRepository pageRepository, EffectRepository effectRepository) {
        this.textRepository = textRepository;
        this.pageRepository = pageRepository;
        this.effectRepository = effectRepository;
    }

    @Override
    public TextDTO addText(TextDTO textDTO) throws TextAlreadyExistException, PageNotFoundException {
        Text text = textRepository.findByPageAndAndDescription(textDTO.getPageId(), textDTO.getShellType()).orElse(null);
        if(text != null)
            throw new TextAlreadyExistException("Text already exist");
        Pages page = pageRepository.findById(textDTO.getPageId()).orElse(null);
        if(page == null){
            throw new PageNotFoundException("Can not add text to does not exist page");
        }
        text = textRepository.save(textDTO.toText(page));
        return TextDTO.fromText(text);
    }

    @Override
    public void addEffect(JoinDTO joinDTO) throws TextNotFoundException, EffectNotFoundException {
        Text text = textRepository.findById(joinDTO.getFirstId()).orElse(null);
        if(text == null)
            throw new TextNotFoundException("Text with id: " + joinDTO.getFirstId() + " not found");
        Effect effect = effectRepository.findById(joinDTO.getSecondId()).orElse(null);
        if(effect == null)
            throw new EffectNotFoundException("Effect with id: " + joinDTO.getSecondId() + " not found");
        textRepository.addEffect(joinDTO.getFirstId(), joinDTO.getSecondId());
    }

    @Override
    public List<TextDTO> getAll() throws TextNotFoundException {
        List<Text> texts = textRepository.findAll();
        if(texts.size() == 0)
            throw new TextNotFoundException("Texts do not exist");
        List<TextDTO> result = new ArrayList<>();
        texts.forEach(text -> result.add(TextDTO.fromText(text)));
        return result;
    }

    @Override
    public TextDTO findById(Long textId) throws TextNotFoundException {
        Text text = textRepository.findById(textId).orElse(null);
        if(text == null)
            throw new TextNotFoundException("Text with id: " + textId + " not found");
        return TextDTO.fromText(text);
    }

    @Override
    public List<TextDTO> findAllByType(ShellType type) throws TextNotFoundException {
        List<Text> texts = textRepository.findAllByType(type.name());
        if(texts.size() == 0)
            throw new TextNotFoundException("Texts with shell type: " + type.name() + " not found");
        List<TextDTO> textDTOS = new ArrayList<>();
        texts.forEach(text -> textDTOS.add(TextDTO.fromText(text)));
        return textDTOS;
    }

    @Override
    public void update(Long textId, TextDTO updatedText) throws TextNotFoundException {
        TextDTO text = this.findById(textId);
        text.checkFields(updatedText);
        textRepository.update(text.getTextId(), text.getPageId(), text.getDescription(), text.getShellType(), text.getFont(), text.getSize());
    }

    @Override
    public void delete(Long textId) throws TextNotFoundException {
        TextDTO text = this.findById(textId);
        textRepository.deleteEffects(text.getTextId());
        textRepository.delete(text.getTextId());
    }
}
