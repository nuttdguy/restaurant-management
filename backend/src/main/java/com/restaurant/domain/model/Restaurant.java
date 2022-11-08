package com.restaurant.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "restaurants")
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
@ToString
public class Restaurant implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type = "org.hibernate.type.UUIDCharType")
    @Column(name = "restaurant_uuid", updatable = false)
    private UUID uuid;

    private String name;
    private String alias;
    private String url;
    private String phone;
    private String category;

    private String description;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String zip;
    private String country;
    private Boolean hasLicense;
    private Boolean active;
    @CreationTimestamp private LocalDateTime createdAt;
    @UpdateTimestamp private LocalDateTime updatedAt;

    @JoinColumn(name = "license_id")
    @OneToOne(fetch = FetchType.LAZY)
    private License license;

    @JoinColumn(name = "user_uuid")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Fetch(value = FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "restaurant")
    @ToString.Exclude
    private Set<Photo> photos = new HashSet<>();

    @Fetch(value = FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    @ToString.Exclude
    private Set<Dish> dishes = new HashSet<>();

    public void addPhoto(Photo photo) {
        this.photos.add(photo);
        photo.setRestaurant(this);
    }

    public void removePhoto(Photo photo) {
        this.photos.remove(photo);
    }

    public void addDish(Dish dish) {
        this.dishes.add(dish);
        dish.setRestaurant(this);
    }
    public void removeDish(Dish dish) {
        this.getDishes().remove(dish);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Restaurant that = (Restaurant) o;
        return uuid != null && Objects.equals(uuid, that.uuid);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
