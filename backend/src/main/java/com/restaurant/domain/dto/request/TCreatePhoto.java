package com.restaurant.domain.dto.request;

import lombok.Builder;

import java.util.Arrays;

@Builder
public record TCreatePhoto(
        Long id,
        String name,
        String type,
        byte[] file,
        String kind,
        TCreateRestaurant restaurant
) {
    public TCreatePhoto {}

    public TCreatePhoto(Long id, String name, String type, byte[] file, String kind) {
        this(id, name, type, file, kind, null);
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TCreatePhoto that = (TCreatePhoto) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (!Arrays.equals(file, that.file)) return false;
        if (kind != null ? !kind.equals(that.kind) : that.kind != null) return false;
        return restaurant != null ? restaurant.equals(that.restaurant) : that.restaurant == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(file);
        result = 31 * result + (kind != null ? kind.hashCode() : 0);
        result = 31 * result + (restaurant != null ? restaurant.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TCreatePhoto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", file=" + Arrays.toString(file) +
                ", kind='" + kind + '\'' +
                ", restaurant=" + restaurant +
                '}';
    }
}
