package com.management.storage.repository;

import com.management.storage.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ItemRepository extends JpaRepository<Item, Long> {
    @Query(value = "SELECT i.* FROM item i, item_type it WHERE i.itemtype_id=it.id AND it.value=:type", nativeQuery = true)
    List<Item> findAllByType(@Param("type") String type);
}
