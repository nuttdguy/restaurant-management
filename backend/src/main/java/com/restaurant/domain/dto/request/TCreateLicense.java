package com.restaurant.domain.dto.request;

import lombok.Builder;

import java.util.Arrays;
import java.util.Objects;

@Builder
public record TCreateLicense(
        Long id,
        String name,
        String type,
        byte[] file
) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TCreateLicense that = (TCreateLicense) o;

        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(name, that.name)) return false;
        if (!Objects.equals(type, that.type)) return false;
        return Arrays.equals(file, that.file);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(file);
        return result;
    }

    @Override
    public String toString() {
        return "TCreateLicense{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", file=" + Arrays.toString(file) +
                '}';
    }
}
