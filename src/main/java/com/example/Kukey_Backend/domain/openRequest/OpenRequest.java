package com.example.Kukey_Backend.domain.openRequest;

import com.example.Kukey_Backend.domain.room.Room;
import com.example.Kukey_Backend.global.entity.BaseEntity;
import com.example.Kukey_Backend.global.entity.RequestStatus;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "open_requests")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OpenRequest extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "open_request_id")
    private Long openRequestId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @Column(name = "processed_at")
    private LocalDateTime processedAt;

    @Column(name = "status", nullable = false)
    private RequestStatus status;
}

