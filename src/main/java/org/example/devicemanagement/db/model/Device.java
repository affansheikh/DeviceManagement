package org.example.devicemanagement.db.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "DEVICES")
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    private String name;

    private String brand;

    public Device() {}
    public Device(String name, String brand) {
        this.name = name;
        this.brand = brand;
    }
    public Long getID() {return ID;}
    public void setID(Long ID) {this.ID = ID;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public String getBrand() {return brand;}
    public void setBrand(String brand) {this.brand = brand;}
}
