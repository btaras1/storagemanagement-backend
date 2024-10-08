package com.management.storage.repository;

import com.management.storage.dto.response.MostSelledDoor;
import com.management.storage.model.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReceiptRepository extends JpaRepository<Receipt, Long> {

    @Query(value = "SELECT COUNT(*) FROM receipt WHERE sold >= date_trunc('month', CURRENT_DATE)", nativeQuery = true)
    public Integer countReceiptsForCurrentMonth();

    @Query(value = "SELECT item.value, item.color_id, count(*) FROM item, receipt, item_receipt, item_type WHERE item.id=item_receipt.item_id AND receipt.id=item_receipt.receipt_id AND item.itemtype_id = item_type.id AND item_type.value = 'DOOR' GROUP BY item.value, item.color_id ORDER BY count desc LIMIT 1", nativeQuery = true)
    public MostSelledDoor mostSoldDoor();

    @Query(value = "SELECT * FROM receipt WHERE sold = (SELECT MAX(sold) FROM receipt)LIMIT 1", nativeQuery = true)
    public Receipt getLastReceipt();

    @Query(value = "SELECT * FROM receipt WHERE mounted = false", nativeQuery = true)
    public List<Receipt> getReceiptNotMounted();
}
