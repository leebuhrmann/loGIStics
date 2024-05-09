package com.logistics.snowapi.repository;

import com.logistics.snowapi.model.Alert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface SubscribedBoundaryAlertRepository extends JpaRepository<Alert, Integer> {
    @Query("SELECT DISTINCT a, b.id, b.name FROM Alert a " +
            "JOIN a.ugcZones uz " +
            "JOIN uz.boundaries b " +
            "WHERE b.subscribed = true")
    List<Object[]> findAllAlertsBySubscribedBoundaries();
}
