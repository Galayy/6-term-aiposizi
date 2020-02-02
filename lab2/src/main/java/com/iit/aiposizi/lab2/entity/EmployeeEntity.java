package com.iit.aiposizi.lab2.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.AUTO;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "employee")
public class EmployeeEntity {

    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "department")
    private String department;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", unique = true, nullable = false)
    private String lastName;

    @OneToOne(fetch = LAZY, mappedBy = "employee")
    private PlaceEntity place;

}
