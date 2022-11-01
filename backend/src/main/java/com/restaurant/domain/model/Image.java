package com.restaurant.domain.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

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

//    @Column(name ="image_bytes", nullable = false, length = 100000)
    @Column(name ="image_bytes", length = 100000)
    private byte[] imageBytes;

    @Column(name = "is_dish_image")
    private Boolean isDishImage;

    @ManyToOne
    @JoinColumn(name = "restaurant_uuid")
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name = "dish_id")
    private Dish dish;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Image image = (Image) o;
        return id != null && Objects.equals(id, image.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
