package com.management.storage.services;

import com.management.storage.model.Color;
import com.management.storage.repository.ColorRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class ColorService {

    ColorRepository colorRepository;

    public List<Color> findAll() {
        return colorRepository.findAll();
    }

    public Color findById(Long id) {
        return colorRepository.getById(id);
    }

    @Transactional
    public Color createOrUpdate(Color color) {
        return colorRepository.save(color);
    }

    public void deleteById(Long id) {
        colorRepository.deleteById(id);
    }
}
