package com.example.Kukey_Backend.domain.keyLocation.domain;

import com.example.Kukey_Backend.domain.room.domain.Room;
import com.example.Kukey_Backend.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "key_locations")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KeyLocation extends BaseEntity {

    @Id
    @Column(name = "key_location_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long keyLocationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @Column(name = "admin_name", length = 30, nullable = false)
    private String adminName;

    @Column(name = "image_url", columnDefinition = "TEXT", nullable = false)
    private String imageUrl;

    @Column(name = "description", nullable = false)
    private String description;
}

