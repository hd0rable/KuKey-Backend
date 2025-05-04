package com.example.Kukey_Backend.domain.room.domain.repository;

import com.example.Kukey_Backend.domain.openRequest.domain.OpenRequest;
import com.example.Kukey_Backend.domain.room.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

}
