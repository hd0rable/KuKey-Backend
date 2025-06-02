package com.example.Kukey_Backend.domain.space.domain;

import com.example.Kukey_Backend.global.entity.BaseEntity;
import com.example.Kukey_Backend.global.entity.RequestStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "spaces")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Space extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "space_id")
    private Long spaceId;

    @Enumerated(EnumType.STRING)
    @Column(name = "building_name", nullable = false)
    private BuildingName buildingName;

    @Column(name = "space_display_name", length = 8, nullable = false)
    private String spaceDisplayName;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "open_status", nullable = false)
    private OpenStatus openStatus;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "open_request_status", nullable = false)
    private RequestStatus openRequestStatus;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "reservation_status", nullable = false)
    private RequestStatus reservationStatus;

    @Setter
    @LastModifiedDate
    @Column(name = "modified_at", nullable = false)
    private LocalDateTime modifiedAt;

}

