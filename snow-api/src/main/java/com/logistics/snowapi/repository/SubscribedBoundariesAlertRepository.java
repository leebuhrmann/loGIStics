package com.logistics.snowapi.repository;

import com.logistics.snowapi.model.Alert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface SubscribedBoundariesAlertRepository extends JpaRepository<Alert, Integer> {
    // Define custom query to fetch alerts for subscribed boundaries
    @Query("SELECT a FROM Alert a " +
            "JOIN a.ugcZones uz " +
            "JOIN uz.boundaries b " +
            "WHERE b.subscribed = true")
    List<Alert> findAllAlertsBySubscribedBoundaries();
}
