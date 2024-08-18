package org.example.devicemanagement.db.repository;

import org.example.devicemanagement.db.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {
    Device findByBrand(String Brand);


}
