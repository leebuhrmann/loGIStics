package com.logistics.snowapi.service;

import com.logistics.snowapi.model.UgcZone;
import com.logistics.snowapi.repository.UgcZoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// TODO CREATE TESTS
@Service
public class UgcZoneService {

    private final UgcZoneRepository ugcZoneRepository;

    @Autowired
    public UgcZoneService(UgcZoneRepository ugcZoneRepository) {
        this.ugcZoneRepository = ugcZoneRepository;
    }

    public List<UgcZone> findAllUgcZones() {
        return ugcZoneRepository.findAll();
    }

    public Optional<UgcZone> findUgcZoneByUgcCode(String ugcCode) {
        return ugcZoneRepository.findByUgcCode(ugcCode);
    }

    public UgcZone createUgcZone(UgcZone ugcZone) {
        UgcZone savedZone = ugcZoneRepository.save(ugcZone);
        eventPublisher.publishEvent(new UgcZoneCreatedEvent(this, savedZone));
        return savedZone;
    }

    public UgcZone updateUgcZone(UgcZone ugcZone) {
        // Make sure the UGC Zone exists before updating
        if (ugcZone.getUgcCode() != null && ugcZoneRepository.existsByUgcCode(ugcZone.getUgcCode())) {
            return ugcZoneRepository.save(ugcZone);
        }
        // Handle the case where the UGC Zone doesn't exist (could throw an exception or return null)
        return null;
    }

    public void deleteUgcZoneByUgcCode(String ugcCode) {
        ugcZoneRepository.deleteByUgcCode(ugcCode);
    }
}

