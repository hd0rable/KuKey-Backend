package com.example.Kukey_Backend.domain.openRequest.domain.repository;

import com.example.Kukey_Backend.domain.openRequest.domain.OpenRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OpenRequestRepository extends JpaRepository<OpenRequest, Long> {

}
