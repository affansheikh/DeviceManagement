package org.example.devicemanagement.controller;

import org.example.devicemanagement.model.Device;
import org.example.devicemanagement.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/devices")
public class DeviceController {
    @Autowired
    DeviceRepository deviceRepository;

    @GetMapping
    public ResponseEntity<List<Device>> getAllDevices(@RequestParam(required = false) String brand) {
        List<Device> devices;
        if (brand == null) devices = deviceRepository.findAll();
        else devices = deviceRepository.findByBrand(brand);
        return ResponseEntity.ok(devices);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Device> getDeviceById(@PathVariable Long id) {
        Device device = deviceRepository.findById(id).orElse(null);
        return ResponseEntity.ok(device);
    }

    @DeleteMapping
    public ResponseEntity<Device> deleteDeviceById(@RequestParam Long id) {
        Device device = deviceRepository.findById(id).orElse(null);
        assert device != null;
        deviceRepository.delete(device);
        return ResponseEntity.ok(device);
    }
}
