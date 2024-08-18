package org.example.devicemanagement.controller;

import org.example.devicemanagement.model.Device;
import org.example.devicemanagement.model.DeviceDTO;
import org.example.devicemanagement.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/devices")
public class DeviceController {
    @Autowired
    DeviceRepository deviceRepository;

    /**
     * Supports two functionalities
     * Get all devices
     * Get all devices by brand
     */
    @GetMapping
    public ResponseEntity<List<Device>> getAllDevices(@RequestParam(required = false) String brand) {
        List<Device> devices;
        if (brand == null)
            devices = deviceRepository.findAll();
        else
            devices = deviceRepository.findByBrand(brand);
        return ResponseEntity.ok(devices);
    }

    @GetMapping("/{id}")
    public ResponseEntity getDeviceById(@PathVariable Long id) {
        Device device = deviceRepository.findById(id).orElse(null);
        if (device == null)
            return ResponseEntity.status(404).body("The device does not exist for this id");
        return ResponseEntity.ok(device);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteDeviceById(@PathVariable Long id) {
        Device device = deviceRepository.findById(id).orElse(null);
        if (device == null)
            return ResponseEntity.status(404).body("The device does not exist for this id");
        deviceRepository.delete(device);
        return ResponseEntity.ok().build();
    }

    /**
     * allows creation of multiple similar devices
     * check README for suggestions for idempotency
     */
    @PostMapping
    public ResponseEntity<Device> createDevice(@RequestBody DeviceDTO deviceBody) {
        Device device = new Device(deviceBody.getName(), deviceBody.getBrand());
        deviceRepository.save(device);
        return ResponseEntity.ok(device);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateDevice(@PathVariable Long id, @RequestBody DeviceDTO deviceBody) {
        Device existingDevice = deviceRepository.findById(id).orElse(null);
        if (existingDevice == null)
            return ResponseEntity.status(404).body("The device does not exist for this id");
        String updatedName = deviceBody.getName() == null ? existingDevice.getName() : deviceBody.getName();
        String updatedBrand = deviceBody.getBrand() == null ? existingDevice.getBrand() : deviceBody.getBrand();
        existingDevice.setName(updatedName);
        existingDevice.setBrand(updatedBrand);
        deviceRepository.save(existingDevice);
        return ResponseEntity.ok(existingDevice);
    }
}
