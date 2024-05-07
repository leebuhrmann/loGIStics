package com.logistics.snowapi.service;

import com.logistics.snowapi.model.UgcZone;
import com.logistics.snowapi.repository.UgcZoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class that encapsulates the business logic for managing UGC (Universal Geographic Code) zones.
 * This class provides methods to find, create, update, and delete {@link UgcZone} entities, using the
 * {@link UgcZoneRepository} for database interactions.
 * <p>
 * Methods in this class handle standard CRUD operations for UGC zones and ensure that the business rules
 * for processing these entities, such as uniqueness checks based on the UGC code, are enforced.
 * <p>
 * Usage:
 * This service should be used whenever UGC zone entities need to be manipulated or queried. The methods
 * provided ensure that UGC zones are handled consistently and in accordance with the defined business rules.
 *
 * @see UgcZone
 * @see UgcZoneRepository
 */
@Service
public class UgcZoneService {

    private final UgcZoneRepository ugcZoneRepository;

    @Autowired
    public UgcZoneService(UgcZoneRepository ugcZoneRepository) {
        this.ugcZoneRepository = ugcZoneRepository;
    }

    /**
     * Retrieves all UGC zones stored in the database.
     *
     * @return a list of {@link UgcZone} entities
     */
    public List<UgcZone> findAllUgcZones() {
        return ugcZoneRepository.findAll();
    }

    /**
     * Retrieves a UGC zone by its unique UGC code.
     *
     * @param ugcCode the UGC code of the zone to retrieve
     * @return an {@link Optional} containing the found UGC zone or empty if no zone is found
     */
    public Optional<UgcZone> findUgcZoneByUgcCode(String ugcCode) {
        return ugcZoneRepository.findByUgcCode(ugcCode);
    }

    /**
     * Creates a new UGC zone in the database.
     *
     * @param ugcZone the {@link UgcZone} entity to create
     * @return the newly created UGC zone
     */
    public UgcZone createUgcZone(UgcZone ugcZone) {
        return ugcZoneRepository.save(ugcZone);
    }

    /**
     * Updates an existing UGC zone in the database. This method assumes the existence of the UGC zone
     * as it does not explicitly handle the case where the UGC zone may not exist.
     *
     * @param ugcZone the {@link UgcZone} entity to update
     * @return the updated UGC zone, or null if it doesn't exist
     */
    public UgcZone updateUgcZone(UgcZone ugcZone) {
        // Make sure the UGC Zone exists before updating
        if (ugcZone.getUgcCode() != null && ugcZoneRepository.existsByUgcCode(ugcZone.getUgcCode())) {
            return ugcZoneRepository.save(ugcZone);
        }
        // Handle the case where the UGC Zone doesn't exist (could throw an exception or return null)
        return null;
    }

    /**
     * Deletes a UGC zone from the database by its UGC code.
     *
     * @param ugcCode the UGC code of the zone to delete
     */
    public void deleteUgcZoneByUgcCode(String ugcCode) {
        ugcZoneRepository.deleteByUgcCode(ugcCode);
    }
}

