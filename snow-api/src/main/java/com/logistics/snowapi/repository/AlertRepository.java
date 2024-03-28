package com.logistics.snowapi.repository;

import com.logistics.snowapi.model.Alert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlertRepository extends JpaRepository<Alert, Integer> {
    boolean existsByNwsID(String nwsID);
}
