package com.management.storage.services;

import com.management.storage.dto.response.BuyerResponseDto;
import com.management.storage.dto.response.MostBuyersFromCityDto;
import com.management.storage.model.Buyer;
import com.management.storage.repository.BuyerRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class BuyerService {

    BuyerRepository buyerRepository;

    public List<BuyerResponseDto> findAll() {
        return mapBuyersToResponse(buyerRepository.findAll());
    }

    public Buyer findById(Long id) {
        return buyerRepository.getById(id);
    }

    @Transactional
    public Buyer createOrUpdate(Buyer buyer) {
        return buyerRepository.save(buyer);
    }

    public void deleteById(Long id) {
        buyerRepository.deleteById(id);
    }

    public MostBuyersFromCityDto mostBuyersFromCity() {
        return buyerRepository.mostBuyersFromCity();
    }

    private List<BuyerResponseDto> mapBuyersToResponse(List<Buyer> buyers) {
      return  buyers.stream().map(buyer ->
              new BuyerResponseDto(
                      buyer.getId(), buyer.getFirstname(),buyer.getLastname(),
                      buyer.getAddress(), buyer.getCity(), buyer.getMobile(),
                      buyer.getReceipts()))
              .collect(Collectors.toList());
    }
}
