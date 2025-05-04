package com.example.Kukey_Backend.domain.room;

import com.example.Kukey_Backend.global.entity.BaseEntity;
import com.example.Kukey_Backend.global.entity.RequestStatus;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "rooms")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Room extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long roomId;

    @Enumerated(EnumType.STRING)
    @Column(name = "building_name", nullable = false)
    private BuildingName buildingName;

    @Column(name = "room_display_name", length = 8, nullable = false)
    private String roomDisplayName;

    @Column(name = "open_status", nullable = false)
    private OpenStatus openStatus;

    @Column(name = "open_request_status", nullable = false)
    private RequestStatus openRequestStatus;

    @Column(name = "reservation_status", nullable = false)
    private RequestStatus reservationStatus;

    @Column(name = "modified_at", nullable = false)
    private LocalDateTime modifiedAt;

}

