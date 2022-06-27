package com.management.storage.controller;


import com.management.storage.dto.request.SetMountRequestDto;
import com.management.storage.dto.response.MostSelledDoorResponseDto;
import com.management.storage.exception.StorageManagementException;
import com.management.storage.model.Receipt;
import com.management.storage.services.ReceiptService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;


@RestController
@RequestMapping("/receipt")
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class ReceiptController {

    ReceiptService receiptService;

    @GetMapping
    List<Receipt> findAll() {
        return receiptService.findAll();
    }

    @GetMapping("{id}")
    public Receipt findById(@PathVariable final Long id) throws StorageManagementException {
        return receiptService.findById(id);
    }

    @PostMapping
    public Receipt create(@Valid @RequestBody final Receipt receipt) {
        return receiptService.create(receipt);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public Receipt update(@Valid @RequestBody final Receipt receipt) {
        return receiptService.update(receipt);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable final Long id) {
        receiptService.deleteById(id);
    }

    @GetMapping("/last")
    public Receipt getLatest() {
        return receiptService.getLatest();
    }

    @GetMapping("/download/{id}")
    public ResponseEntity downloadFromDB(@PathVariable final Long id) throws IOException {
        return receiptService.downloadFromDB(id);
    }

    @GetMapping("/most-selled-door")
    public MostSelledDoorResponseDto getMostSoldDoor() {
        return receiptService.getMostSoldDoor();
    }

    @GetMapping("/count-receipts-current-month")
    public Integer countReceiptsForCurrentMonth() {
        return receiptService.countReceiptsForCurrentMonth();
    }

    @RequestMapping(value = "/mount/{id}", method = RequestMethod.PUT)
    public Receipt setMounted(@PathVariable final Long id, @RequestBody final SetMountRequestDto setMountRequest) {
        return receiptService.setMounted(id, setMountRequest);
    }

    @RequestMapping(value = "/mount-false", method = RequestMethod.GET)
    public List<Receipt> getAllNotMounted() {
        return receiptService.getAllNotMounted();
    }
}
