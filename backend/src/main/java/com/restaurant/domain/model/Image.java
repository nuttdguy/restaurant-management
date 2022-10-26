package com.restaurant.domain.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "images")
@Getter @Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "image_id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name ="image_bytes", nullable = false, length = 100000)
    private byte[] imageBytes;

    @Column(name = "is_dish_image")
    private Boolean isDishImage;

    @ManyToOne
    @JoinColumn(name = "restaurant_uuid")
    private Restaurant restaurant;

}
