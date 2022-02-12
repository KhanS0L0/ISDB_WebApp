package com.example.service.implementations;

import com.example.dto.PivotDTO.EffectDTO;
import com.example.entity.pivots.plot.Effect;
import com.example.exceptions.notFoundExceptions.EffectNotFoundException;
import com.example.repository.EffectRepository;
import com.example.service.interfaces.EffectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EffectServiceImpl implements EffectService {

    private final EffectRepository effectRepository;

    @Autowired
    public EffectServiceImpl(EffectRepository effectRepository) {
        this.effectRepository = effectRepository;
    }

    @Override
    public EffectDTO addEffect(EffectDTO effectDTO){
        Effect effect = effectDTO.toEffect();
        effect = effectRepository.save(effect);
        return EffectDTO.fromEffect(effect);
    }

    @Override
    public List<EffectDTO> getAll() {
        List<Effect> effects = effectRepository.findAll();
        List<EffectDTO> result = new ArrayList<>();
        effects.forEach(effect -> result.add(EffectDTO.fromEffect(effect)));
        return result;
    }

    @Override
    public EffectDTO findById(Long effectId) throws EffectNotFoundException {
        Effect effect = effectRepository.findById(effectId).orElse(null);
        if(effect == null)
            throw new EffectNotFoundException("Effect with id: " + effectId + " not found");
        return EffectDTO.fromEffect(effect);
    }

    @Override
    public List<EffectDTO> findAllByType(String type) throws EffectNotFoundException {
        List<Effect> effects = effectRepository.findAllByEffectType(type);
        if(effects.size() == 0)
            throw new EffectNotFoundException("Effects with type: " + type + " not found");
        List<EffectDTO> result = new ArrayList<>();
        effects.forEach(effect -> result.add(EffectDTO.fromEffect(effect)));
        return result;
    }

    @Override
    public List<EffectDTO> findAllByPage(Long pageId) throws EffectNotFoundException {
        List<Effect> effects = effectRepository.findByPage(pageId);
        if(effects.size() == 0)
            throw new EffectNotFoundException("Page with id: " + pageId + " does not have any effects");
        List<EffectDTO> result = new ArrayList<>();
        effects.forEach(effect -> result.add(EffectDTO.fromEffect(effect)));
        return result;
    }

    @Override
    public void update(Long effectId, EffectDTO updatedEffect) throws EffectNotFoundException {
        EffectDTO effect = this.findById(effectId);
        effect.checkFields(updatedEffect);
        effectRepository.updateById(effect.getId(), effect.getType(), effect.getDescription());
    }

    @Override
    public void delete(Long effectId) throws EffectNotFoundException {
        this.findById(effectId);
        effectRepository.deleteFromPages(effectId);
        effectRepository.deleteEffect(effectId);
    }
}
