package com.restaurant.domain.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "safety_licenses")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SafetyLicense {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "license_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "license", nullable = false, length = 10000000)
    private byte[] license;

    @OneToOne
    @JoinColumn(name = "restaurant_uuid")
    private Restaurant restaurant;

}
