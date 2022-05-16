package com.management.storage.repository;

import com.management.storage.dto.response.FullDetailItemsInStorage;
import com.management.storage.dto.response.ItemsInStorageResponse;
import com.management.storage.model.Storage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StorageRepository extends JpaRepository<Storage, Long> {

    @Query(value = "SELECT it.id, it.value as value, it.description,  ity.value as type, col.value as color, it.guide_needed as guideNeeded, SUM(its.quantity) as quantity\n" +
            "            FROM item it LEFT JOIN item_storage its ON it.id = its.item_id\n" +
            "            LEFT JOIN item_type ity ON ity.id=it.itemtype_id\n" +
            "\t\t\tLEFT JOIN color col ON col.id=it.color_id\n" +
            "            GROUP BY it.id, ity.value, it.description, col.value", nativeQuery = true)
    List<ItemsInStorageResponse> allItemsInStorage();

    @Query(value = "SELECT s.id as storageid, it.id as itemid, it.description, it.value as value,  ity.value as type, col.value as color, it.guide_needed as guideNeeded, its.quantity as quantity,\n" +
            "(SELECT SUM(itr.quantity) FROM receipt r1, item_receipt itr, storage s1, item i1 \n" +
            "WHERE r1.id=itr.receipt_id AND s1.id = s.id AND \n" +
            " i1.id = it.id AND itr.item_id = i1.id\n" +
            "AND itr.storage_id = s1.id AND r1.mounted=false) as mountedquantity\n" +
            " FROM item it LEFT JOIN item_storage its ON it.id = its.item_id\n" +
            "LEFT JOIN storage s ON s.id = its.storage_id\n" +
            "LEFT JOIN item_type ity ON ity.id=it.itemtype_id\n" +
            "LEFT JOIN color col ON col.id=it.color_id\n" +
            "LEFT JOIN item_receipt itr ON itr.item_id = it.id AND itr.storage_id = s.id\n" +
            " GROUP BY s.id, it.id, ity.value, it.description, col.value, its.quantity, mountedquantity\n" +
            "ORDER BY s.id", nativeQuery = true)
    List<FullDetailItemsInStorage> test();
    @Query(value = "SELECT it.id, it.value as value, it.description, ity.value as type, col.value as color, it.guide_needed as guideNeeded, SUM(itr.quantity) as quantity\n" +
            "    FROM item it LEFT JOIN item_receipt itr ON it.id = itr.item_id\n" +
            "    LEFT JOIN receipt r ON r.id = itr.receipt_id\n" +
            "    LEFT JOIN item_type ity ON ity.id=it.itemtype_id\n" +
            "    LEFT JOIN color col ON col.id=it.color_id\n" +
            "    WHERE r.id = itr.receipt_id AND r.mounted = false\n" +
            "    GROUP BY it.id, ity.value, it.description, col.value;", nativeQuery = true)
    List<ItemsInStorageResponse> allItemsInStorageNotMounted();



    @Query(value = "SELECT SUM(its.quantity) as quantity FROM item it LEFT JOIN item_storage its ON it.id = its.item_id LEFT JOIN item_type ity ON ity.id=it.itemtype_id LEFT JOIN color col ON col.id=it.color_id",nativeQuery = true)
    public Integer sumAllItemsInStorage();
}
