package com.example.demo.entity;

import com.example.demo.enums.Priority;
import com.example.demo.enums.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Ticket extends BaseEntity {

    private String iin;

    private Status status;

    private Priority priority;

    private LocalDateTime dateTime;

    @ManyToOne
    private Window window;

    @ManyToOne
    private User user;

    public void prePersist() {
        this.dateTime = LocalDateTime.now();
    }

}


