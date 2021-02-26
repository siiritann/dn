package com.example.datanor.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "user_cities")
@IdClass(UserCityId.class)
public class UserCity implements Serializable {

    @Id
    private Long cityId;
    @Id
    private Long userId;

    public UserCity() {
    }

    public UserCity(Long cityId, Long userId) {
        this.cityId = cityId;
        this.userId = userId;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
