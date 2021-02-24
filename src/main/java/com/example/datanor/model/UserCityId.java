package com.example.datanor.model;

import java.io.Serializable;
import java.util.Objects;

public class UserCityId implements Serializable {

    private Long cityId;
    private Long userId;

    public UserCityId() {
    }

    public UserCityId(Long cityId, Long userId) {
        this.cityId = cityId;
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserCityId userCity = (UserCityId) o;
        return cityId.equals(userCity.cityId) &&
                userId.equals(userCity.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cityId, userId);
    }

}
