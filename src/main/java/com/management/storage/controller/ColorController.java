package com.management.storage.controller;


import com.management.storage.model.Color;
import com.management.storage.services.ColorService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@RestController
@RequestMapping("/color")
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class ColorController {

    ColorService colorService;

    @GetMapping
    public List<Color> findAll() {
        return colorService.findAll();
    }

    @GetMapping("{id}")
    public Color findById(@PathVariable final Long id) {
        return colorService.findById(id);
    }

    @PostMapping
    public Color create(@Valid @RequestBody final Color color) {
        return colorService.createOrUpdate(color);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public Color update(@Valid @RequestBody final Color color) {
        return colorService.createOrUpdate(color);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable final Long id) {
        colorService.deleteById(id);
    }
}
