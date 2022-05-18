package com.management.storage.repository;

import com.management.storage.dto.response.MostBuyersFromCity;
import com.management.storage.model.Buyer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BuyerRepository extends JpaRepository<Buyer, Long> {
    @Query(value = "SELECT buyer.city, count(*) FROM receipt, buyer WHERE receipt.buyer_id=buyer.id GROUP BY buyer.city ORDER BY count desc LIMIT 1", nativeQuery = true)
    public MostBuyersFromCity mostBuyersFromCity();
}
