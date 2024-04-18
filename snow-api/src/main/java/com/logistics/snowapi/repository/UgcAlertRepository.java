package com.logistics.snowapi.repository;

import com.logistics.snowapi.model.UgcAlert;
import com.logistics.snowapi.model.UgcAlertId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UgcAlertRepository extends JpaRepository<UgcAlert, UgcAlertId> {
}
