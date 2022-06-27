package com.management.storage.controller;


import com.management.storage.model.Procurement;
import com.management.storage.services.ProcurementService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@RestController
@RequestMapping("/procurement")
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class ProcurementController {

    ProcurementService procurementService;

    @GetMapping
    public List<Procurement> findAll() {
        return procurementService.findAll();
    }

    @GetMapping("{id}")
    public Procurement findById(@PathVariable final Long id) {
        return procurementService.findById(id);
    }

    @PostMapping
    public Procurement create(@RequestBody final Procurement procurement) {
        return procurementService.create(procurement);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity downloadFromDB(@PathVariable final Long id) throws IOException {
        return procurementService.downloadFromDB(id);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public Procurement update(@RequestBody final Procurement procurement) {
        return procurementService.update(procurement);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable final Long id) {
        procurementService.deleteById(id);
    }
}
