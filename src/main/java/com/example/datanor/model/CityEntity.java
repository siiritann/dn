package com.example.datanor.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cities")
public class CityEntity {
    @Id
    Long id;
    String name;
    String countryCode;
    String stateCode;


    public CityEntity() {
    }

    public CityEntity(Long id, String name, String countryCode, String stateCode) {
        this.id = id;
        this.name = name;
        this.countryCode = countryCode;
        this.stateCode = stateCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }
}
