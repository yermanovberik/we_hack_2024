package com.example.demo.repository;

import com.example.demo.entity.Ticket;
import com.example.demo.entity.Window;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WindowRepository extends JpaRepository<Window, Long> {
}
