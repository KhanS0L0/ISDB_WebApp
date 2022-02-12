package com.example.service.interfaces;

import com.example.dto.PivotDTO.EffectDTO;
import com.example.exceptions.notFoundExceptions.EffectNotFoundException;

import java.util.List;

public interface EffectService {

    EffectDTO addEffect(EffectDTO effectDTO);

    List<EffectDTO> getAll();

    EffectDTO findById(Long effectId) throws EffectNotFoundException;

    List<EffectDTO> findAllByType(String type) throws EffectNotFoundException;

    List<EffectDTO> findAllByPage(Long pageId) throws EffectNotFoundException;

    void update(Long effectId, EffectDTO updatedEffect) throws EffectNotFoundException;

    void delete(Long effectId) throws EffectNotFoundException;
}
