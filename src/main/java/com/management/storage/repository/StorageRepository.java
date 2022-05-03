package com.management.storage.repository;

import com.management.storage.dto.response.ItemsInStorageResponse;
import com.management.storage.model.Storage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StorageRepository extends JpaRepository<Storage, Long> {

    @Query(value = "SELECT it.id, it.value as value,  ity.value as type, col.value as color, it.guide_needed as guideNeeded, SUM(its.quantity) as quantity\n" +
            "            FROM item it LEFT JOIN item_storage its ON it.id = its.item_id\n" +
            "            LEFT JOIN item_type ity ON ity.id=it.itemtype_id\n" +
            "\t\t\tLEFT JOIN color col ON col.id=it.color_id\n" +
            "            GROUP BY it.id, ity.value, col.value", nativeQuery = true)
    List<ItemsInStorageResponse> allItemsInStorage();

    @Query(value = "SELECT SUM(its.quantity) as quantity FROM item it LEFT JOIN item_storage its ON it.id = its.item_id LEFT JOIN item_type ity ON ity.id=it.itemtype_id LEFT JOIN color col ON col.id=it.color_id",nativeQuery = true)
    public Integer sumAllItemsInStorage();
}
