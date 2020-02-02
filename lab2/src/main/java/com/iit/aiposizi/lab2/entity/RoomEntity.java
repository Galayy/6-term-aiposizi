package com.iit.aiposizi.lab2.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.AUTO;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "room")
public class RoomEntity {

    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "office_id", referencedColumnName = "id")
    private OfficeEntity office;

    @Column(name = "number", nullable = false)
    private Integer number;

    @OneToMany(fetch = LAZY, mappedBy = "room", cascade = ALL)
    private List<PlaceEntity> places;

}
