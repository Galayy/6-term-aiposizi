package com.iit.aiposizi.lab2.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

import static javax.persistence.GenerationType.AUTO;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "place")
public class PlaceEntity {

    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private EmployeeEntity employee;

    @ManyToOne
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    private RoomEntity room;

    @Column(name = "number", nullable = false)
    private Integer number;

}
