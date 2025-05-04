package com.example.Kukey_Backend.domain.room;

import lombok.Getter;

@Getter
public enum BuildingName {

    ENGINEERING("공학관"),
    NEW_ENGINEERING("신공학관"),
    MILLENNIUM("새천년관");

    private final String buildingName;

    BuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

}
