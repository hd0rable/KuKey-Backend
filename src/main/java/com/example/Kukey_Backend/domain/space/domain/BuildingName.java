package com.example.Kukey_Backend.domain.space.domain;

import com.example.Kukey_Backend.global.exception.GlobalException;
import lombok.Getter;

import static com.example.Kukey_Backend.global.response.status.BaseExceptionResponseStatus.INVALID_BUILDING_TYPE;

@Getter
public enum BuildingName {

    ENGINEERING("공학관"),
    NEW_ENGINEERING("신공학관"),
    MILLENNIUM("새천년관");

    private final String buildingName;

    BuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public static BuildingName from(String value) {
        for (BuildingName buildingName : BuildingName.values()) {
            if (buildingName.getBuildingName().equals(value)) {
                return buildingName;
            }
        }
        throw new GlobalException(INVALID_BUILDING_TYPE);
    }

}
