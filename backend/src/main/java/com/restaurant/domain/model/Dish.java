package com.restaurant.domain.model;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;


@Entity
@Table(name = "dishes")
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@ToString
@Builder
public class Dish implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "dish_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "category")
    private String category;

    @Column(name = "tags")
    private String tags;

    @Column(name = "description")
    private String description;

    @Column(name = "ingredients")
    private String ingredients;

    @Column(name = "price")
    private BigDecimal price;

    @JoinColumn(name = "restaurant_uuid")
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Restaurant restaurant;

    @Fetch(value = FetchMode.SUBSELECT) // one query mode
    @OneToMany(mappedBy = "dish", cascade = CascadeType.ALL)
    @ToString.Exclude
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
