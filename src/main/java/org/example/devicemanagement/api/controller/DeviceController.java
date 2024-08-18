package org.example.devicemanagement.api.controller;

import org.example.devicemanagement.api.utils.DeviceDTO;
import org.example.devicemanagement.domain.model.Device;
import org.example.devicemanagement.domain.service.DeviceService;
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
    DeviceService deviceService;

    /**
     * Supports two functionalities
     * Get all devices
     * Get all devices by brand
     */
    @GetMapping
    public ResponseEntity<List<Device>> getAllDevices(@RequestParam(required = false) String brand) {
        List<Device> devices = deviceService.getAllDevices(brand);
        return ResponseEntity.ok(devices);
    }

    @GetMapping("/{id}")
    public ResponseEntity getDeviceById(@PathVariable Long id) {
        Device device = deviceService.getDeviceById(id);
        if (device == null)
            return ResponseEntity.status(404).body("The device does not exist for this id");
        return ResponseEntity.ok(device);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteDeviceById(@PathVariable Long id) {
        Device device = deviceService.getDeviceById(id);
        if (device == null)
            return ResponseEntity.status(404).body("The device does not exist for this id");
        deviceService.deleteDeviceById(id);
        return ResponseEntity.ok().build();
    }

    /**
     * allows creation of multiple similar devices
     * check README for suggestions for idempotency
     */
    @PostMapping
    public ResponseEntity<Device> createDevice(@RequestBody DeviceDTO deviceBody) {
        Device device = new Device(deviceBody.getName(), deviceBody.getBrand());
        return ResponseEntity.ok(deviceService.createDevice(device));
    }

    @PutMapping("/{id}")
    public ResponseEntity updateDevice(@PathVariable Long id, @RequestBody DeviceDTO deviceBody) {
        Device existingDevice = deviceService.getDeviceById(id);
        if (existingDevice == null)
            return ResponseEntity.status(404).body("The device does not exist for this id");
        return ResponseEntity.ok(deviceService.updateDevice(deviceBody, existingDevice));
    }
}
