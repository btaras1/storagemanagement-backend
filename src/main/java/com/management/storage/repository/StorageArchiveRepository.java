package com.management.storage.repository;

import com.management.storage.model.StorageArchive;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StorageArchiveRepository extends JpaRepository<StorageArchive, Long> {
}
