package com.management.storage.controller;

import com.management.storage.dto.response.BuyerResponseDto;
import com.management.storage.dto.response.MostBuyersFromCityDto;
import com.management.storage.model.Buyer;
import com.management.storage.services.BuyerService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@RestController
@RequestMapping("/buyer")
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class BuyerController {

    BuyerService buyerService;

    @GetMapping
    public List<BuyerResponseDto> findAll() {
        return buyerService.findAll();
    }

    @GetMapping("{id}")
    public Buyer findById(@PathVariable final Long id) {
        return buyerService.findById(id);
    }

    @PostMapping
    public Buyer create(@Valid @RequestBody final Buyer buyer) {
        return buyerService.createOrUpdate(buyer);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public Buyer update(@Valid @RequestBody final Buyer buyer) {
        return buyerService.createOrUpdate(buyer);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable final Long id) {
        buyerService.deleteById(id);
    }

    @GetMapping("/top-city")
    public MostBuyersFromCityDto topCity() {
        return buyerService.mostBuyersFromCity();
    }


}
