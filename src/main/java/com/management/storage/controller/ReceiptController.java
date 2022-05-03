package com.management.storage.controller;


import com.management.storage.dto.response.MostSelledDoor;
import com.management.storage.dto.response.MostSelledDoorResponse;
import com.management.storage.model.Receipt;
import com.management.storage.repository.ColorRepository;
import com.management.storage.repository.ReceiptRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/receipt")
public class ReceiptController {
    @Autowired
    private ReceiptRepository receiptRepository;

    @Autowired
    private ColorRepository colorRepository;

    @GetMapping
    List<Receipt> findAll(){return receiptRepository.findAll();}

    @GetMapping("{id}")
    public Receipt findById(@PathVariable Long id){return receiptRepository.getById(id);}

    @PostMapping
    public Receipt create(@RequestBody final Receipt receipt){return receiptRepository.saveAndFlush(receipt);}

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public Receipt update(@PathVariable Long id, @RequestBody Receipt receipt){
        Receipt currentReceipt = receiptRepository.getById(id);
        BeanUtils.copyProperties(receipt, currentReceipt, "id");
        return receiptRepository.saveAndFlush(currentReceipt);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id){
        receiptRepository.deleteById(id);
    }

    @GetMapping("/last")
    public Receipt getLastest() {
        return receiptRepository.getLastReceipt();
    }

    @GetMapping("/most-selled-door")
    public MostSelledDoorResponse getMostSelledDoor() {
        MostSelledDoor mostSelledDoor = receiptRepository.mostSelledDoor();
        String doorColor = colorRepository.getById(mostSelledDoor.getColor_Id()).getValue();
        String name = mostSelledDoor.getValue() + " - " + doorColor;
        return new MostSelledDoorResponse(name, mostSelledDoor.getCount());

    }

    @GetMapping("/count-receipts-current-month")
    public Integer countReceiptsForCurrentMonth() {
        return receiptRepository.countReceiptsForCurrentMonth();
    }
}
