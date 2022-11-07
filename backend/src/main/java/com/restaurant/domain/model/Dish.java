package com.restaurant.domain.model;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.*;


@Entity
@Table(name = "dishes")
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
public class Dish {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "dish_id")
    private Long id;

    private String name;
    private String category;
    private String tags;
    private String description;
    private String ingredients;
    private BigDecimal price;

    @JoinColumn(name = "restaurant_uuid")
    @ManyToOne
    private Restaurant restaurant;

    @Fetch(value = FetchMode.SUBSELECT) // one query mode
    @OneToMany(mappedBy = "dish", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Photo> photos = new HashSet<>();

    public void addPhoto(Photo photo) {
        this.photos.add(photo);
        photo.setDish(this);
    }

    public void removePhoto(Photo photo) {
        this.photos.remove(photo);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Dish dish = (Dish) o;
        return id != null && Objects.equals(id, dish.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
