package org.example.devicemanagement.domain.service;

import org.example.devicemanagement.api.utils.DeviceDTO;
import org.example.devicemanagement.db.repository.DeviceRepository;
import org.example.devicemanagement.domain.model.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;

    public List<Device> getAllDevices(String brand) {
        if (brand == null)
            return deviceRepository.findAll();
        else
            return deviceRepository.findByBrand(brand);
    }

    public Device getDeviceById(Long id) {
        return deviceRepository.findById(id).orElse(null);
    }

    public void deleteDeviceById(Long id) {
        deviceRepository.deleteById(id);
    }

    public Device createDevice(Device device) {
        return deviceRepository.save(device);
    }

    public Device updateDevice(DeviceDTO deviceBody, Device currentDevice) {
        String updatedName = deviceBody.getName() == null ? currentDevice.getName() : deviceBody.getName();
        String updatedBrand = deviceBody.getBrand() == null ? currentDevice.getBrand() : deviceBody.getBrand();
        currentDevice.setName(updatedName);
        currentDevice.setBrand(updatedBrand);
        return deviceRepository.save(currentDevice);
    }
}
