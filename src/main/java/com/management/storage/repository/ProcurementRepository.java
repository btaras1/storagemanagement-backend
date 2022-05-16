package com.management.storage.repository;

import com.management.storage.model.Procurement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProcurementRepository extends JpaRepository<Procurement, Long> {
    public List<Procurement> findAllByOrderByIdDesc();
}
