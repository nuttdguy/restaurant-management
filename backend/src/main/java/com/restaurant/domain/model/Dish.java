package com.restaurant.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@ToString
public class Dish {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "dish_id")
    private Long id;

    private String name;
    private String description;
    private BigDecimal price;
    private String ingredients;


    @ManyToOne
    @JoinColumn(name = "restaurant_uuid")
    @JsonIgnore
    private Restaurant restaurant;

    @OneToMany(mappedBy = "dish", cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SUBSELECT) // one query mode
    @JsonIgnore
    @ToString.Exclude
    private Set<Photo> photos = new HashSet<>();

    public void addImage(Photo photo) {
        this.photos.add(photo);
    }

    public void removeImage(Photo photo) {
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
