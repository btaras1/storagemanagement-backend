package com.management.storage.controller;


import com.management.storage.model.Color;
import com.management.storage.repository.ColorRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/color")
public class ColorController {
    @Autowired
    private ColorRepository colorRepository;

    @GetMapping
    List<Color> findAll(){return colorRepository.findAll();}

    @GetMapping("{id}")
    public Color findById(@PathVariable Long id){return colorRepository.getById(id);}

    @PostMapping
    public Color create(@RequestBody final Color color){return colorRepository.saveAndFlush(color);}

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public Color update(@PathVariable Long id, @RequestBody Color color){
        Color currentColor = colorRepository.getById(id);
        BeanUtils.copyProperties(color, currentColor, "id");
        return colorRepository.saveAndFlush(currentColor);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id){
        colorRepository.deleteById(id);
    }
 }
