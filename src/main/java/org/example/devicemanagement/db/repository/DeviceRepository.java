package org.example.devicemanagement.db.repository;

import org.example.devicemanagement.domain.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {
    List<Device> findByBrand(String Brand);
}
