package com.restaurant.domain.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "safety_licenses")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class License {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "license_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "license", nullable = false, length = 10000000)
    private byte[] file;

    @Column(name ="file_url")
    private String fileUrl;

    @OneToOne(mappedBy = "license", fetch = FetchType.LAZY)
    private Restaurant restaurant;

}

